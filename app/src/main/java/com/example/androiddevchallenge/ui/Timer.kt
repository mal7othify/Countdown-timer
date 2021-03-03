/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.animation.AnimatedCircle
import com.example.androiddevchallenge.ui.util.toTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.TickerMode
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@Composable
fun Timer() {
    val time = remember { mutableStateOf(3600000L) }
    val ticker: ReceiveChannel<Unit> =
        ticker(0, 20, Dispatchers.Default, TickerMode.FIXED_PERIOD)
    var tickerJob: Job? = null
    GlobalScope.launch(Dispatchers.Default) {
        tickerJob = GlobalScope.launch(Dispatchers.Main) {
            for (i in ticker) {
                if (time.value > 0) {
                    time.value -= 1
                } else {
                    break
                }
            }
        }
    }
    Box(Modifier.fillMaxSize()) {
        AnimatedCircle(

            modifier = Modifier
                .height(350.dp)
                .align(Alignment.Center)
                .fillMaxWidth()
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = time.value.toTime,
            style = TextStyle(
                color = Color.DarkGray,
                fontSize = 45.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}
