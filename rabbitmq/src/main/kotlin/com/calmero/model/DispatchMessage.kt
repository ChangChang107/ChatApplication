package com.calmero.model

import com.calmero.type.MessageType
import java.io.Serializable

data class DispatchMessage<T>(
    val messageType: MessageType,
    val payload: T
) : Serializable

