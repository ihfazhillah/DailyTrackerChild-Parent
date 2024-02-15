package com.ihfazh.dailytrackerchild_parent

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ihfazh.dailytrackerchild_parent.pages.home.HomeScreen
import com.ihfazh.dailytrackerchild_parent.pages.login.LoginScreen
import com.ihfazh.dailytrackerchild_parent.pages.login.LoginViewModel
import com.ihfazh.dailytrackerchild_parent.pages.task_list.TaskListScreen
import com.ihfazh.dailytrackerchild_parent.pages.task_list.TaskListViewModel

@Composable
fun DailyTrackerComposeParentApp(){
    val navController = rememberNavController()
    DailyTrackerNavHost(navController = navController)
}


@Composable
fun DailyTrackerNavHost(
    navController: NavHostController
){

    val activity = (LocalContext.current as MainActivity)

    NavHost(navController = navController, startDestination = "login"){
        composable("login"){
            LoginScreen(
                viewModel = viewModel(factory = LoginViewModel.Factory) ,
                onLoginSuccess = {
                    navController.navigate("child-picker" ){
                        popUpTo("login"){
                            inclusive = true
                        }
                    }
                }
            )
        }
        
        composable("child-picker"){
                HomeScreen(
                    onProfileClicked = {profile ->
//                        navController.navigate("task-list/${profile.id}")
                    }
                )
        }

        composable("task-list/{childId}"){ backStackEntry ->
            val childId = backStackEntry.arguments?.getString("childId") ?: return@composable  Text(text = "Hello world")
            val childrenCache = (activity.application as DailyTrackerChildApplication).compositionRoot.childrenCache
            val profile = childrenCache.getProfile(childId) ?: return@composable Text(text="Children not found")

            TaskListScreen(
                viewModel = viewModel(factory = TaskListViewModel.Factory(profile)),
                onProfileClicked = {
                    navController.navigateUp()
                }
            )
        }

    }

}