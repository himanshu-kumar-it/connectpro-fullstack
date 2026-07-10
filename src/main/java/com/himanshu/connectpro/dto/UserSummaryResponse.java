
package com.himanshu.connectpro.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSummaryResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String headline;
    private String location;
    private String profileImage;
}