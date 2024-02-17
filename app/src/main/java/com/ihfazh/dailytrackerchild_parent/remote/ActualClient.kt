package com.ihfazh.dailytrackerchild_parent.remote

import com.ihfazh.dailytrackerchild_parent.components.Task
import com.ihfazh.dailytrackerchild_parent.components.TaskStatus
import com.ihfazh.dailytrackerchild_parent.fp.Outcome
import com.ihfazh.dailytrackerchild_parent.fp.OutcomeError
import com.ihfazh.dailytrackerchild_parent.utils.TokenCache
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType


class TokenHeaderConfiguration {
    lateinit var tokenCache: TokenCache
}



val TokenHeader = createClientPlugin("TokenHeader", ::TokenHeaderConfiguration){
    val tokenCache = pluginConfig.tokenCache

    onRequest { request, content ->
        if ("auth-token" !in request.url.toString()){
            request.headers.append("Authorization", "Token ${tokenCache.getToken()}")
        }
    }
}

class ActualClient(
    private val httpClient: HttpClient,
    private val tokenCache: TokenCache
): Client {

    private val baseUrl = "https://cms.ksatriamuslim.com"
    private val childrenTaskBase = "/api/children-tasks"

    override suspend fun login(body: LoginBody): Outcome<LoginResponse, OutcomeError> {
        val response = httpClient.post("$baseUrl/auth-token/"){
            contentType(ContentType.Application.Json)
            setBody(body)
        }

        return try {
            val data = response.body<LoginResponse>()
            tokenCache.saveToken(data.token)
            Outcome.success(data)
        } catch (e: Exception) {
            Outcome.failure(OutcomeError("Username / Password salah."))
        }

    }

    override suspend fun getChildren(): Outcome<ChildrenResponse, OutcomeError> {
        val url = "$baseUrl$childrenTaskBase/children/"

        val response = httpClient.get(url)

        return try {
            val data = response.body<ChildrenResponse>()
            Outcome.success(data)
        } catch (e: Exception){
            println(e.stackTraceToString())
            Outcome.failure(OutcomeError("something error"))
        }
    }

    override suspend fun getTaskList(id: String): Outcome<TaskListResponse, OutcomeError> {
        val url = "$baseUrl$childrenTaskBase/children/$id/tasks/"
        val response = httpClient.get(url)
        return try {
            Outcome.success(response.body<TaskListFromRemoteResponse>().toTaskListResponse())
        } catch (e: Exception){
            println(e.stackTraceToString())
            Outcome.failure(OutcomeError(e.stackTraceToString()))
        }
    }

    override suspend fun markTaskAsFinished(id: String): Outcome<Task, OutcomeError> {
        val url = "$baseUrl$childrenTaskBase/mark-as-finished/"
        val response = httpClient.post(url){
            contentType(ContentType.Application.Json)
            setBody(MarkAsFinishedBody(id))
        }
        return try {
            Outcome.success(response.body<MarkAsFinishedResponse>().task.toTask())
        } catch (e: Exception){
            println(e.stackTraceToString())
            Outcome.failure(OutcomeError(e.stackTraceToString()))
        }
    }

    override suspend fun confirmTask(id: String): Outcome<Task, OutcomeError> {
        val url = "$baseUrl$childrenTaskBase/confirm/"
        val response = httpClient.post(url){
            contentType(ContentType.Application.Json)
            setBody(MarkAsFinishedBody(id))
        }
        return try {
            Outcome.success(response.body<MarkAsFinishedResponse>().task.toTask())
        } catch (e: Exception){
            Outcome.failure(OutcomeError(e.stackTraceToString()))
        }
    }
}

private fun TaskListFromRemoteResponse.toTaskListResponse(): TaskListResponse = TaskListResponse(
    tasks = tasks.map { taskRemote  ->  taskRemote.toTask()}
)

private fun TaskRemote.toTask(): Task {
    return Task(id, title, TaskStatus.valueOf(status), image)
}
