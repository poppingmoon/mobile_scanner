package dev.steenbakker.mobile_scanner.objects

import zxingcpp.BarcodeReader

enum class BarcodeFormats(val value: BarcodeReader.Format) {
    UNKNOWN(BarcodeReader.Format.NONE),
    ALL_FORMATS(BarcodeReader.Format.NONE),
    CODE_128(BarcodeReader.Format.CODE_128),
    CODE_39(BarcodeReader.Format.CODE_39),
    CODE_93(BarcodeReader.Format.CODE_93),
    CODABAR(BarcodeReader.Format.CODABAR),
    DATA_MATRIX(BarcodeReader.Format.DATA_MATRIX),
    EAN_13(BarcodeReader.Format.EAN_13),
    EAN_8(BarcodeReader.Format.EAN_8),
    ITF(BarcodeReader.Format.ITF),
    QR_CODE(BarcodeReader.Format.QR_CODE),
    UPC_A(BarcodeReader.Format.UPC_A),
    UPC_E(BarcodeReader.Format.UPC_E),
    PDF417(BarcodeReader.Format.PDF_417),
    AZTEC(BarcodeReader.Format.AZTEC);

    companion object {
        fun fromRawValue(rawValue: Int): BarcodeFormats {
            return when (rawValue) {
                -1 -> UNKNOWN
                0 -> ALL_FORMATS
                1 -> CODE_128
                2 -> CODE_39
                4 -> CODE_93
                8 -> CODABAR
                16 -> DATA_MATRIX
                32 -> EAN_13
                64 -> EAN_8
                126, 127, 128 -> ITF
                256 -> QR_CODE
                512 -> UPC_A
                1024 -> UPC_E
                2048 -> PDF417
                4096 -> AZTEC
                else -> UNKNOWN
            }
        }
    }
}