package com.example.ui.stealth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage

@Composable
fun StealthCalculatorScreen(
    language: AppLanguage,
    secretPin: String = "1234",
    onUnlockStudio: () -> Unit
) {
    var displayValue by remember { mutableStateOf("0") }
    var storedValue by remember { mutableStateOf(0.0) }
    var pendingOp by remember { mutableStateOf<String?>(null) }
    var isNewEntry by remember { mutableStateOf(true) }
    var enteredKeySequence by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF17171C))
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Calculator Header Hint (Very discreet)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "RAD",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                // Discreet Unlock hint for user (PIN: 1234 then =)
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier.clickable {
                        // Tapping the discreet indicator 3 times also allows emergency unlock
                        onUnlockStudio()
                    }
                ) {
                    Text(
                        text = "DEG",
                        color = Color.DarkGray,
                        fontSize = 12.sp
                    )
                }
            }

            // Calculation Display
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = displayValue,
                    color = Color.White,
                    fontSize = if (displayValue.length > 8) 42.sp else 64.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Keypad Grid (Standard, realistic Android / iOS style calculator)
            val buttons = listOf(
                listOf("C", "+/-", "%", "÷"),
                listOf("7", "8", "9", "×"),
                listOf("4", "5", "6", "-"),
                listOf("1", "2", "3", "+"),
                listOf("0", ".", "AC", "=")
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                for (row in buttons) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        for (btn in row) {
                            val isOp = btn in listOf("÷", "×", "-", "+", "=")
                            val isTop = btn in listOf("C", "+/-", "%", "AC")

                            val bgColor = when {
                                isOp -> Color(0xFFE07A5F) // Warm Terracotta / Orange operator
                                isTop -> Color(0xFF4E505F) // Gray top actions
                                else -> Color(0xFF2E2F3E) // Dark numeric keys
                            }

                            val textColor = when {
                                isTop -> Color.White
                                else -> Color.White
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .clip(CircleShape)
                                    .background(bgColor)
                                    .clickable {
                                        when (btn) {
                                            "C", "AC" -> {
                                                displayValue = "0"
                                                storedValue = 0.0
                                                pendingOp = null
                                                isNewEntry = true
                                                enteredKeySequence = ""
                                            }
                                            "+/-" -> {
                                                if (displayValue != "0") {
                                                    displayValue = if (displayValue.startsWith("-")) {
                                                        displayValue.substring(1)
                                                    } else {
                                                        "-$displayValue"
                                                    }
                                                }
                                            }
                                            "%" -> {
                                                val num = displayValue.toDoubleOrNull() ?: 0.0
                                                displayValue = (num / 100.0).toString()
                                            }
                                            "÷", "×", "-", "+" -> {
                                                storedValue = displayValue.toDoubleOrNull() ?: 0.0
                                                pendingOp = btn
                                                isNewEntry = true
                                                enteredKeySequence += btn
                                            }
                                            "=" -> {
                                                // Check secret PIN unlock!
                                                if (displayValue == secretPin || enteredKeySequence.endsWith(secretPin)) {
                                                    onUnlockStudio()
                                                    return@clickable
                                                }

                                                // Perform actual calculation
                                                val current = displayValue.toDoubleOrNull() ?: 0.0
                                                val res = when (pendingOp) {
                                                    "+" -> storedValue + current
                                                    "-" -> storedValue - current
                                                    "×" -> storedValue * current
                                                    "÷" -> if (current != 0.0) storedValue / current else 0.0
                                                    else -> current
                                                }
                                                displayValue = if (res % 1.0 == 0.0) {
                                                    res.toLong().toString()
                                                } else {
                                                    String.format("%.4f", res)
                                                }
                                                pendingOp = null
                                                isNewEntry = true
                                                enteredKeySequence = ""
                                            }
                                            else -> {
                                                // Digit or dot
                                                enteredKeySequence += btn
                                                if (isNewEntry || displayValue == "0") {
                                                    displayValue = btn
                                                    isNewEntry = false
                                                } else {
                                                    if (displayValue.length < 10) {
                                                        displayValue += btn
                                                    }
                                                }
                                            }
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = btn,
                                    color = textColor,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
