package com.kursatkumsuz.market

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.kursatkumsuz.market.component.MarketList
import com.kursatkumsuz.market.component.RowListShimmerEffect
import com.kursatkumsuz.ui.components.ColumnListShimmerEffect
import kotlinx.coroutines.delay

@Composable
fun MarketScreen(navController: NavHostController) {

    val viewModel: MarketViewModel = hiltViewModel()

    var refreshState by remember { mutableStateOf(false) }

    val loadingState = viewModel.loadingState.value

    val currentBalance = viewModel.currentBalance.value

    LaunchedEffect(refreshState) {
        if (refreshState) {
            viewModel.refresh()
            delay(100)
            refreshState = false
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshState),
        onRefresh = { refreshState = true }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.primary),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "Balance", fontSize = 16.sp, color = Color.LightGray)
                    Text(
                        text = "$${String.format("%.2f", currentBalance)}",
                        fontSize = 28.sp,
                        color = Color.White,
                        fontWeight = Bold
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))

                if (loadingState) {
                    ShimmerContent()
                } else {
                    MarketList(
                        navController,
                        coinList = viewModel.cryptoList.value,
                        viewModel.popularList.value
                    )
                }
            }
        }
    }
}

@Composable
fun ShimmerContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary),
    ) {
        RowListShimmerEffect()
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "Market Data",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
        )
        Spacer(modifier = Modifier.height(10.dp))
        ColumnListShimmerEffect()
    }
}







