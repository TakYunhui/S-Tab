package com.ssafy.stab.modals

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.ssafy.stab.R
import com.ssafy.stab.screens.space.share.SpaceViewModel

@Composable
fun SpaceNameEditModal(
    closeModal: () -> Unit,
    spaceId: String,
    currentTitle: String,
    onRename: (String) -> Unit,
    spaceViewModel: SpaceViewModel
) {
    var newTitle by remember { mutableStateOf(currentTitle) }

    AlertDialog(
        onDismissRequest = closeModal,
        title = { Text("스페이스 이름 변경", fontFamily = FontFamily.Default) },
        text = {
            TextField(
                value = newTitle,
                onValueChange = {newValue ->
                    if (newValue.isEmpty() || newValue.first() != ' ') {
                        newTitle = newValue
                    }
                },
                label = { Text("새로운 이름", fontFamily = FontFamily.Default) },
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.6f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                ),
                singleLine = true
            )
        },
        confirmButton = {
            Button(
                onClick = {
                onRename(newTitle)
                spaceViewModel.renameShareSpace(spaceId, newTitle)
                closeModal() },
                ) {
                Text("변경", fontFamily = FontFamily.Default)
            }
        },
        dismissButton = {
            Button(onClick = closeModal,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = MaterialTheme.colorScheme.primary // 생성 버튼의 글자색 사용
                )) {
                Text("취소", fontFamily = FontFamily.Default)
            }
        }
    )
}