package com.notebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.notebook.data.NoteDatabase
import com.notebook.navigation.NotebookNavigation
import com.notebook.ui.viewmodel.NoteViewModelFactory
import com.notebook.ui.theme.NotebookTheme
import com.notebook.ui.viewmodel.NoteViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val database = NoteDatabase.getDatabase(applicationContext)
        val noteDao = database.noteDao()
        val viewModelFactory = NoteViewModelFactory(noteDao)
        
        setContent {
            NotebookTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: NoteViewModel = viewModel(factory = viewModelFactory)
                    NotebookNavigation(viewModel = viewModel)
                }
            }
        }
    }
}