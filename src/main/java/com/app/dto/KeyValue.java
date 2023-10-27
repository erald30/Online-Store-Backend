package com.app.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KeyValue <k,v>{
    protected k key;
    protected v value;
}
