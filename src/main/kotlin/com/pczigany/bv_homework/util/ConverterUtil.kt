package com.pczigany.bv_homework.util

import com.pczigany.bv_homework.data.input.CommentRequest
import com.pczigany.bv_homework.data.document.Comment as CommentDocument

object ConverterUtil {
    fun CommentRequest.asDocument() =
        CommentDocument(timestamp = this.timestamp, message = this.message)
}
