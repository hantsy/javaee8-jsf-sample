/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.hantsy.ee8sample.service;

/**
 *
 * @author Hantsy <hantsy@gmail.com>
 */
public class InvalidUsernameException extends RuntimeException {

    public InvalidUsernameException(String message) {
        super(message +" is invalid");
    }

}
