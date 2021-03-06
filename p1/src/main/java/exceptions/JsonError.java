/* Copyright © 2015 Oracle and/or its affiliates. All rights reserved. */
package exceptions;

public class JsonError {
  private String type;
  private String message;
  
  public JsonError(String type, String message){
    this.type = type;
    this.message = message;        
  }
  
  public String getType(){
    return this.type;
  }
  
  public String getMessage(){
    return this.message;
  }
}
