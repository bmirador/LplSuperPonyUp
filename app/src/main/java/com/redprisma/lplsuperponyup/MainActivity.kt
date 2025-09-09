package com.redprisma.lplsuperponyup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.data.AssetPathState
import com.redprisma.lplsuperponyup.ui.screens.home.CommentRoute
import com.redprisma.lplsuperponyup.ui.screens.home.CommentRoute.CommentList
import com.redprisma.lplsuperponyup.ui.screens.home.CommentListScreen
import com.redprisma.lplsuperponyup.ui.screens.home.CommentListState
import com.redprisma.lplsuperponyup.ui.theme.LplSuperPonyUpTheme
import com.redprisma.lplsuperponyup.ui.util.longClickForMock
import com.redprisma.lplsuperponyup.ui.screens.home.CommentsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


// Entry point for the app with Hilt dependency injection enabled
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // ViewModel scoped to this activity
    private val commentsViewModel: CommentsViewModel by viewModels()

    @Inject
    lateinit var assetPathState: AssetPathState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Trigger initial data load
        commentsViewModel.loadComments()
        // Set the Jetpack Compose UI content
        setContent {
            // Collect UI state from ViewModel with lifecycle awareness
            val homeState = commentsViewModel.homeState.value

            // Apply custom app theme
            LplSuperPonyUpTheme {

                val modifier =
                    if (BuildConfig.IS_MOCK) Modifier.longClickForMock(assetPathState) else Modifier
                Scaffold(
                    modifier = modifier,
                    content = { innerPadding ->
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                        ) {
                            EntryProviderDsl(
                                loadComments = commentsViewModel::loadComments,
                                homeState = homeState
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun EntryProviderDsl(loadComments: () -> Unit, homeState: CommentListState) {
    val backStack = rememberNavBackStack(CommentList)
    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            HomeSection(
                homeState = homeState,
                loadComments = loadComments
            )
            DetailComment()
        }
    )
}

@Composable
private fun EntryProviderBuilder<NavKey>.DetailComment() {
    entry<CommentRoute.CommentDetail>(
        metadata = mapOf("commentId" to "1")
    ) { key -> Text("Product") }
}

@Composable
private fun EntryProviderBuilder<NavKey>.HomeSection(
    homeState: CommentListState,
    loadComments: () -> Unit
) {
    entry<CommentList> {
        Column {
            CommentListScreen(
                modifier = Modifier.fillMaxSize(),
                homeState = homeState,
                loadComments = loadComments
            )
        }
    }
}
