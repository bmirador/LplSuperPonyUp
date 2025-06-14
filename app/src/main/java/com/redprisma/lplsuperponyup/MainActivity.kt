package com.redprisma.lplsuperponyup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.redprisma.lplsuperponyup.ui.Route
import com.redprisma.lplsuperponyup.ui.screens.CommentListScreen
import com.redprisma.lplsuperponyup.ui.theme.LplSuperPonyUpTheme
import com.redprisma.lplsuperponyup.ui.viewmodels.CommentsViewModel
import dagger.hilt.android.AndroidEntryPoint

// Entry point for the app with Hilt dependency injection enabled
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // ViewModel scoped to this activity
    private val commentsViewModel: CommentsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Trigger initial data load
        commentsViewModel.loadComments()

        // Set the Jetpack Compose UI content
        setContent {
            // Collect UI state from ViewModel with lifecycle awareness
            val homeState = commentsViewModel.homeState.collectAsStateWithLifecycle(this).value

            // Navigation controller for managing screen navigation
            val navController = rememberNavController()

            // Apply custom app theme
            LplSuperPonyUpTheme {
                Scaffold(
                    content = { innerPadding ->

                        // Create navigation graph with defined routes
                        val graph =
                            navController.createGraph(startDestination = Route.Home.route) {
                                composable(route = Route.Home.route) {
                                    // Show list of comments
                                    CommentListScreen(
                                        homeState
                                    ) { commentsViewModel.loadComments() }
                                }
                            }

                        // Main layout container with padding
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                        ) {
                            // Host for rendering the current screen based on navigation
                            NavHost(
                                navController = navController,
                                graph = graph
                            )
                        }
                    }
                )
            }
        }
    }
}
