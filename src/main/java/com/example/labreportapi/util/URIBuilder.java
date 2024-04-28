package com.example.labreportapi.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class URIBuilder {

    public static URI getResourceLocation() {
        return ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    }

    public static URI getResourceLocation(int id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }

}
