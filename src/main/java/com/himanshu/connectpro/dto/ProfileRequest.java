
package com.himanshu.connectpro.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProfileRequest {

    @Size(max = 150)
    private String headline;

    private String about;

    @Size(max = 100)
    private String location;

    private String profileImage;

    private String coverImage;
}