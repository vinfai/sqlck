package com.vertigrated.text.encode;

import com.vertigrated.text.StringUtil;


/**
 * Soundex codes begin with the first letter of the surname followed by a
 * three-digit code that represents the first three remaining consonants. <br>
 * Zeros will be added to names that do not have enough letters to be coded. <br>
 * Soundex Coding Guide (Consonants that sound alike have the same code)
 * <p/>
 * <p/>
 * 1 - B,P,F,V <br>
 * 2 - C,S,G,J,K,Q,X,Z<br>
 * 3 - D,T <br>
 * 4 - L <br>
 * 5 - M,N <br>
 * 6 - R <br>
 * </p>
 * <p/>
 * <p/>
 * The letters A,E,I,O,U,Y,H, and W are not coded.<br>
 * Names with adjacent letters having the same equivalent number are coded as
 * one letter with a single number.<br>
 * Surname prefixes are generally not used in the soundex.<br>
 * </p>
 */
public final class Soundex implements Encoder
{
    /**
     * Mapping.
     */
    private static final char[] map = "01230120022455012623010202".toCharArray();

    /**
     * to be compatible with regular soundex codes limit the code length to 4.<br>
     * soundex codes with a length greater than 4 will result in
     * incompatiblities with other soundex engines.
     *
     * @param in input string
     * @return encoded string
     */
    public String encode(final String in)
    {
        if (in != null)
        {
            final StringBuilder sb = new StringBuilder(StringUtil.stringOfChar('0', 4));
            char last;
            char mapped;
            int incount = 1;
            int count = 1;
            sb.setCharAt(0, Character.toUpperCase(in.charAt(0)));
            last = getMappingCode(in.charAt(0));
            incount = incount + 1;
            mapped = getMappingCode(in.charAt(incount));

            while ((incount < in.length()) && (mapped != 0) &&
                    (count < 4))
            {
                if ((mapped != '0') && (mapped != last))
                {
                    sb.setCharAt(count++, mapped);
                }

                last = mapped;
            }

            return sb.toString();
        }
        else
        {
            return null;
        }
    }

    public String encode(String inputString, int encodeLimit)
    {
        throw new UnsupportedOperationException("Soundex does not support variable length encoding!");
    }

    private static char getMappingCode(char c)
    {
        if (Character.isLetter(c))
        {
            return map[Character.toUpperCase(c) - 'A'];
        }
        else
        {
            return 0;
        }
    }
}
