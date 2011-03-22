package com.vertigrated.text.encode;

/**
 * All String/text encoders should implement this interface.
 */
public interface Encoder
{
    public String encode(String inputString);

    public String encode(String inputString, int encodeLimit);
}
