package com.example.android_practices.ui.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun DetailScreen(
    postId: Int,
    navController: NavController,
    viewModel: DetailViewModel = viewModel()
) {
    LaunchedEffect(postId) { viewModel.load(postId) }

    val post by viewModel.post.collectAsStateWithLifecycle()
    val comments by viewModel.comments.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "← Назад",
            modifier = Modifier.clickable { navController.popBackStack() },
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(8.dp))

        post?.let { p ->
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (title, body) = createRefs()
                Text(
                    text = p.title,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
                Text(
                    text = p.body,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.constrainAs(body) {
                        top.linkTo(title.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                "Комментарии (${comments.size})",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(comments, key = { it.id }) { c ->
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(12.dp)) {
                            Text(c.name, style = MaterialTheme.typography.labelLarge)
                            Text(
                                c.email,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(c.body, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        } ?: Text("Загрузка...")
    }
}