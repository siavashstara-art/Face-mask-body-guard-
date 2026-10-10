package com.example.ui.facecard

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.theme.*
import com.example.viewmodel.FaceGuardViewModel

@Composable
fun FaceCardMainHostScreen(
    viewModel: FaceGuardViewModel,
    onNavigateToStudio: () -> Unit,
    onNavigateToGallery: () -> Unit
) {
    val activeTab by viewModel.faceCardTab.collectAsStateWithLifecycle()
    val cards by viewModel.faceCardRepo.cards.collectAsStateWithLifecycle()
    val activeCardId by viewModel.faceCardRepo.activeCardId.collectAsStateWithLifecycle()
    val loyaltyStampCards by viewModel.faceCardRepo.loyaltyStampCards.collectAsStateWithLifecycle()
    val salonSession by viewModel.faceCardRepo.salonSession.collectAsStateWithLifecycle()
    val sakhtemanPass by viewModel.faceCardRepo.sakhtemanPass.collectAsStateWithLifecycle()
    val marketingStats by viewModel.faceCardRepo.marketingStats.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = WarmSurface,
                tonalElevation = 8.dp,
                modifier = Modifier.border(1.dp, WarmBorder)
            ) {
                // Tab 1: Smart Cards (کارت ویزیت دیجیتال)
                NavigationBarItem(
                    selected = activeTab == "cards",
                    onClick = { viewModel.selectFaceCardTab("cards") },
                    icon = { Icon(Icons.Default.CreditCard, contentDescription = "Cards") },
                    label = { Text("کارت هوشمند", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TerracottaAccent,
                        selectedTextColor = TerracottaAccent,
                        indicatorColor = TerracottaSubtle,
                        unselectedIconColor = CharcoalSecondary,
                        unselectedTextColor = CharcoalSecondary
                    )
                )

                // Tab 2: Loyalty (باشگاه مشتریان و تمبر دیجیتال)
                NavigationBarItem(
                    selected = activeTab == "loyalty",
                    onClick = { viewModel.selectFaceCardTab("loyalty") },
                    icon = { Icon(Icons.Default.CardGiftcard, contentDescription = "Loyalty") },
                    label = { Text("باشگاه مشتریان", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TerracottaAccent,
                        selectedTextColor = TerracottaAccent,
                        indicatorColor = TerracottaSubtle,
                        unselectedIconColor = CharcoalSecondary,
                        unselectedTextColor = CharcoalSecondary
                    )
                )

                // Tab 3: Ecosystem (اکوسیستم سالن‌یار، ساختمان‌یار و آکادمی)
                NavigationBarItem(
                    selected = activeTab == "ecosystem",
                    onClick = { viewModel.selectFaceCardTab("ecosystem") },
                    icon = { Icon(Icons.Default.Hub, contentDescription = "Ecosystem") },
                    label = { Text("اکوسیستم توانا", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TerracottaAccent,
                        selectedTextColor = TerracottaAccent,
                        indicatorColor = TerracottaSubtle,
                        unselectedIconColor = CharcoalSecondary,
                        unselectedTextColor = CharcoalSecondary
                    )
                )

                // Tab 4: Studio Camera & Privacy (استودیو ویدیو و معرفی پرتره)
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToStudio,
                    icon = { Icon(Icons.Default.CameraAlt, contentDescription = "Camera Studio") },
                    label = { Text("استودیو ضبط", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = CharcoalSecondary,
                        unselectedTextColor = CharcoalSecondary
                    )
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(WarmBackground)
        ) {
            AnimatedContent(
                targetState = activeTab,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "FaceCardTabTransition"
            ) { tab ->
                when (tab) {
                    "loyalty" -> CustomerLoyaltyScreen(
                        cards = loyaltyStampCards,
                        onPunchStamp = { cardId -> viewModel.faceCardRepo.punchStamp(cardId) }
                    )
                    "ecosystem" -> EcosystemHubScreen(
                        ecosystemController = viewModel.ecosystemController,
                        maisonRepo = viewModel.maisonRepo,
                        salonSession = salonSession,
                        sakhtemanPass = sakhtemanPass,
                        marketingStats = marketingStats,
                        onSimulateDoorUnlock = { /* Simulated unlock */ },
                        onNavigateToStudio = onNavigateToStudio
                    )
                    else -> FaceCardDashboardScreen(
                        cards = cards,
                        activeCardId = activeCardId,
                        onSelectCard = { cardId -> viewModel.faceCardRepo.selectActiveCard(cardId) },
                        onShareVCard = { card -> viewModel.faceCardRepo.exportAndShareVCard(card) },
                        onNavigateToStudio = onNavigateToStudio,
                        onNavigateToAdmin = { viewModel.navigateTo("admin") }
                    )
                }
            }
        }
    }
}
