package dev.steenbakker.mobile_scanner

import android.R.attr.valueType
import android.graphics.ImageFormat
import android.graphics.Point
import android.graphics.Rect
import android.graphics.YuvImage
import android.media.Image
import java.io.ByteArrayOutputStream
import zxingcpp.BarcodeReader

fun Image.toByteArray(): ByteArray {
    val yBuffer = planes[0].buffer // Y
    val vuBuffer = planes[2].buffer // VU

    val ySize = yBuffer.remaining()
    val vuSize = vuBuffer.remaining()

    val nv21 = ByteArray(ySize + vuSize)

    yBuffer.get(nv21, 0, ySize)
    vuBuffer.get(nv21, ySize, vuSize)

    val yuvImage = YuvImage(nv21, ImageFormat.NV21, this.width, this.height, null)
    val out = ByteArrayOutputStream()
    yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 50, out)
    return out.toByteArray()
}

val BarcodeReader.Result.data: Map<String, Any?>
    get() = mapOf(
        "calendarEvent" to null,
        "contactInfo" to null,
        "corners" to listOf(
            position.topLeft.data,
            position.topRight.data,
            position.bottomRight.data,
            position.bottomLeft.data,
        ),
        "displayValue" to text,
        "driverLicense" to null,
        "email" to null,
        "format" to when (format) {
            BarcodeReader.Format.CODE_128 -> 1
            BarcodeReader.Format.CODE_39 -> 2
            BarcodeReader.Format.CODE_93 -> 4
            BarcodeReader.Format.CODABAR -> 8
            BarcodeReader.Format.DATA_MATRIX -> 16
            BarcodeReader.Format.EAN_13 -> 32
            BarcodeReader.Format.EAN_8 -> 64
            BarcodeReader.Format.ITF -> 128
            BarcodeReader.Format.QR_CODE -> 256
            BarcodeReader.Format.UPC_A -> 512
            BarcodeReader.Format.UPC_E -> 1024
            BarcodeReader.Format.PDF_417 -> 2048
            BarcodeReader.Format.AZTEC -> 4096
            else -> -1
        },
        "geoPoint" to null,
        "phone" to null,
        "rawBytes" to bytes,
        "rawValue" to text,
        "size" to position.size,
        "sms" to null,
        "type" to valueType,
        "url" to null,
        "wifi" to null,
    )

private val Point.data: Map<String, Double>
    get() = mapOf("x" to x.toDouble(), "y" to y.toDouble())

private val BarcodeReader.Position.size: Map<String, Any?>
    get() {
        // Rect.isValid can't be accessed for some reason, so just do the check manually.
        val left = topLeft.x
        val right = topRight.x
        val top = topLeft.y
        val bottom = bottomLeft.y
        if (left <= right && top <= bottom) {
            return mapOf(
                "width" to (right - left).toDouble(),
                "height" to (bottom - top).toDouble()
            )
        }

        return emptyMap()
    }