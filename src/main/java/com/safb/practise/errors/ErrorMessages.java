package com.safb.practise.errors;

public enum ErrorMessages
{
  MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
  RECORD_ALREADY_EXISTS("Record already exists"),
  INTERNAL_SERVER_ERROR("Internal server error"),
  NO_RECORD_FOUND("Record with provided ID has not been found"),
  COLLECTION_EMPTY("The requested collection is empty"),
  AUTHENTICATION_FAILED("Authentication failed"),
  COULD_NOT_UPDATE_RECORD("Could not update record"),
  COULD_NOT_DELETE_RECORD("Could not delete record"),
  EMAIL_ADDRESS_NOT_VERIFIED("Email addres could not be verified"),
  FIELD_NOT_MATCHING_CRITERIA("Some field/s does not match the required criteria"),
  OBJECT_EMPTY("The given object is null or empty"),
  USER_EXISTS("This user already exists"),
  USER_NO_EXIST("The user does not exist");

  private String errorMessage;

  ErrorMessages(String errorMessage)
  {
    this.errorMessage = errorMessage;
  }

  /**
   * @return the error message
   */
  public String getErrorMessage()
  {
    return errorMessage;
  }

  /**
   * @param errorMessage the error message to be set
   */
  public void setErrorMessage(String errorMessage)
  {
    this.errorMessage = errorMessage;
  }
}
