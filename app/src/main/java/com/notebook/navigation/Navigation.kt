package com.notebook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.notebook.ui.screens.NoteEditScreen
import com.notebook.ui.screens.NotesListScreen
import com.notebook.ui.viewmodel.NoteViewModel

sealed class Screen(val route: String) {
    object NotesList : Screen("notes_list")
    object NoteEdit : Screen("note_edit/{noteId}") {
        fun createRoute(noteId: Long?) = "note_edit/${noteId ?: "new"}"
    }
}

@Composable
fun NotebookNavigation(
    viewModel: NoteViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.NotesList.route
    ) {
        composable(Screen.NotesList.route) {
            NotesListScreen(
                viewModel = viewModel,
                onNoteClick = { note ->
                    navController.navigate(Screen.NoteEdit.createRoute(note.id))
                },
                onAddNoteClick = {
                    navController.navigate(Screen.NoteEdit.createRoute(null))
                }
            )
        }
        composable(
            route = Screen.NoteEdit.route,
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = "new"
                }
            )
        ) { backStackEntry ->
            val noteIdString = backStackEntry.arguments?.getString("noteId")
            val noteId = if (noteIdString == "new" || noteIdString == null) null else noteIdString.toLongOrNull()
            
            NoteEditScreen(
                viewModel = viewModel,
                noteId = noteId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

