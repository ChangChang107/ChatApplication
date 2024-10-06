package com.calmero.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliverRequest extends ParentMessageRequest {
    private String messageId;
    private String recipientId;
}
