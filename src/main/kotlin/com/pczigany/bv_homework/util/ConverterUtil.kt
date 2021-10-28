package com.pczigany.bv_homework.util

import com.pczigany.bv_homework.data.input.CommentRequest
import java.text.SimpleDateFormat
import java.util.Date
import com.pczigany.bv_homework.data.document.Comment as CommentDocument

object ConverterUtil {

    fun CommentRequest.asDocument() = CommentDocument(timestamp = this.timestamp, message = this.message)

    fun String.toDate(): Date = SimpleDateFormat("yyyy-MM-dd").parse(this)

    fun CharSequence.toDate() = this.toString().toDate()
}
