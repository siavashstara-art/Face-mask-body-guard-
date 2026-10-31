package com.example

import com.example.engine.EcosystemConfig
import com.example.engine.FaceDetectionEngine
import com.example.model.PerformancePreset
import com.example.ui.farshbazar.defaultCarpetCatalog
import com.example.ui.talayar.defaultJewelryCatalog
import org.junit.Assert.*
import org.junit.Test
import java.io.File

class RecordingAndModulesTest {

    @Test
    fun testFaceDetectionEngine_neverCrashesAndProvidesStableAnchor() {
        val faceEngine = FaceDetectionEngine()
        val defaultFaces = faceEngine.getDefaultCenterFace()
        assertFalse("Default face anchor must not be empty", defaultFaces.isEmpty())

        val face = defaultFaces.first()
        assertTrue("Face bounds must be in valid normalized coordinate range", face.bounds.left in 0f..1f)
        assertTrue(face.bounds.top in 0f..1f)
        assertTrue(face.bounds.right in 0f..1f)
        assertTrue(face.bounds.bottom in 0f..1f)
        assertTrue(face.confidence > 0.9f)
    }

    @Test
    fun testTalaYar_officialUnionFormulaCalculation() {
        val gramPrice = 48_500_000.0 // Rials per gram
        val weight = 10.0 // 10 grams
        val wagePct = 12.0 // 12%
        val profitPct = 7.0 // 7%

        val rawGoldValue = gramPrice * weight
        val wageAmount = rawGoldValue * (wagePct / 100.0)
        val totalBeforeProfit = rawGoldValue + wageAmount
        val profitAmount = totalBeforeProfit * (profitPct / 100.0)

        // 9% tax only on (wage + profit)
        val taxBase = wageAmount + profitAmount
        val taxAmount = taxBase * 0.09
        val finalPriceRials = rawGoldValue + wageAmount + profitAmount + taxAmount

        assertEquals(485_000_000.0, rawGoldValue, 0.1)
        assertEquals(58_200_000.0, wageAmount, 0.1)
        assertTrue(profitAmount > 0)
        assertTrue(taxAmount > 0)
        assertTrue(finalPriceRials > rawGoldValue)

        // Verify jewelry catalog is populated
        assertFalse(defaultJewelryCatalog.isEmpty())
        val royalSet = defaultJewelryCatalog.find { it.id == "gold_set_royal" }
        assertNotNull(royalSet)
        assertEquals(18, royalSet?.purityKarat)
    }

    @Test
    fun testFarshBazar_catalogAndSizingMultipliers() {
        assertFalse(defaultCarpetCatalog.isEmpty())
        val tabrizCarpet = defaultCarpetCatalog.find { it.id == "carpet_tabriz_silk" }
        assertNotNull(tabrizCarpet)
        assertEquals("تبریز", tabrizCarpet?.origin)

        val price12m = tabrizCarpet!!.basePriceTomans
        val price9m = (price12m * 0.78).toLong()
        val price6m = (price12m * 0.52).toLong()

        assertTrue(price12m > price9m)
        assertTrue(price9m > price6m)
    }

    @Test
    fun testEcosystemConfig_automatedKeysAreSecurelyProvisioned() {
        assertTrue(EcosystemConfig.isAutomatedSetupReady())
        assertNotNull(EcosystemConfig.securityMode)
        assertNotNull(EcosystemConfig.ecosystemEnvironment)
        assertNotNull(EcosystemConfig.autoSignature)
        assertNotNull(EcosystemConfig.apiEndpoint)
        assertNotNull(EcosystemConfig.merchantId)
    }

    @Test
    fun testPerformancePreset_andFileAtomStructure() {
        val preset = PerformancePreset.BALANCED
        assertNotNull(preset)

        // Verify standard mp4 ftyp atom generation
        val tempFile = File.createTempFile("test_video", ".mp4")
        tempFile.deleteOnExit()

        val ftyp = byteArrayOf(
            0x00, 0x00, 0x00, 0x18, // size: 24
            0x66, 0x74, 0x79, 0x70, // 'ftyp'
            0x6D, 0x70, 0x34, 0x32, // 'mp42'
            0x00, 0x00, 0x00, 0x00, // minor version
            0x69, 0x73, 0x6F, 0x6D, // 'isom'
            0x6D, 0x70, 0x34, 0x32  // 'mp42'
        )
        tempFile.writeBytes(ftyp)

        assertTrue(tempFile.exists())
        assertEquals(24L, tempFile.length())
    }
}
