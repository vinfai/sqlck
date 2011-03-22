package com.vertigrated.text.encode;

public final class MetaPhone implements Encoder
{
    private static final int CHAR_NOT_FOUND = -1;

    /**
     * <H2>
     * Metaphone reduces the alphabet to 16 consonant sounds:<br>
     * B X S K J T F H L M N P R 0 W Y<br>
     * That isn't an O but a zero - representing the 'th' sound.<br>
     * <H3>
     * Metaphone uses the following transformation rules:<br>
     * Doubled letters except "c" -> drop 2nd letter. Vowels are only kept
     * when they are the first letter.
     * <PRE>
     * B -> B   unless at the end of a word after "m" as in "dumb"
     * C -> X    (sh) if -cia- or -ch-
     * S   if -ci-, -ce- or -cy-
     * K   otherwise, including -sch-
     * D -> J   if in -dge-, -dgy- or -dgi-
     * T   otherwise
     * F -> F
     * G ->     silent if in -gh- and not at end or before a vowel
     * in -gn- or -gned- (also see dge etc. above)
     * J   if before i or e or y if not double gg
     * K   otherwise
     * H ->     silent if after vowel and no vowel follows
     * H   otherwise
     * J -> J
     * K ->     silent if after "c"
     * K   otherwise
     * L -> L
     * M -> M
     * N -> N
     * P -> F   if before "h"
     * P   otherwise
     * Q -> K
     * R -> R
     * S -> X   (sh) if before "h" or in -sio- or -sia-
     * S   otherwise
     * T -> X   (sh) if -tia- or -tio-
     * 0   (th) if before "h"
     * silent if in -tch-
     * T   otherwise
     * V -> F
     * W ->     silent if not followed by a vowel
     * W   if followed by a vowel
     * X -> KS
     * Y ->     silent if not followed by a vowel
     * Y   if followed by a vowel
     * Z -> S
     * </PRE>
     * <H3>
     * <PRE>
     * Initial  kn-, gn- pn, ae- or wr-      -> drop first letter
     * Initial  x-                           -> change to "s"
     * Initial  wh-                          -> change to "w"
     * </PRE>
     * The code is truncated at 4 characters in this example, but more could be used.<br>
     * Lawrence Philips, "Hanging on the Metaphone", Computer Language v7 n12,
     * December 1990, pp39-43.<br>
     *
     * @param inputString DOCUMENT ME!
     * @return TODO: DOCUMENT ME!
     */
    public String encode(final String inputString)
    {
        return encode(inputString, 4);
    }

    /**
     * this algorithm is only effective on English words. this came from the
     * internet it needs to be re-implmented it does not handle certain single
     * character codes correctly
     *
     * @param inputString DOCUMENT ME!
     * @param encodeLimit DOCUMENT ME!
     * @return TODO: DOCUMENT ME!
     */
    public String encode(final String inputString, final int encodeLimit)
    {
        final StringBuilder sb = new StringBuilder();
        final String encodeMe = new String(inputString.toUpperCase());
        int index;
        int lastidx = 0;
        int offset = 0;
        int length = encodeMe.length();
        lastidx = length - 1;

        for (index = 0; offset < encodeLimit; index++)
        {
            if (index > lastidx)
            {
                break;
            }

            if (index > 0)
            {
                if (encodeMe.charAt(index) == encodeMe.charAt(index - 1))
                {
                    continue;
                }
            }

            if (encodeMe.charAt(index) == 'A')
            {
                if (index < lastidx)
                {
                    if (encodeMe.charAt(index + 1) == 'E')
                    {
                        sb.insert(offset, 'E');
                        offset++;
                        index += 1;

                        continue;
                    }
                }

                if (index == 0)
                {
                    sb.insert(offset, encodeMe.charAt(index));
                    offset++;
                }

                continue;
            }

            if (encodeMe.charAt(index) == 'B')
            {
                if (index < lastidx)
                {
                    sb.insert(offset, encodeMe.charAt(index));
                    offset++;

                    continue;
                }
                else
                {
                    if (encodeMe.charAt(index - 1) != 'M')
                    {
                        sb.insert(offset, encodeMe.charAt(index));
                        offset++;

                        continue;
                    }
                }
            }

            if (encodeMe.charAt(index) == 'C')
            {
                if (index > 0)
                {
                    if (encodeMe.regionMatches(index - 1, "SCI", 0, 3))
                    {
                        index += 1;

                        continue;
                    }

                    if (encodeMe.regionMatches(index - 1, "SCE", 0, 3))
                    {
                        index += 1;

                        continue;
                    }

                    if (encodeMe.regionMatches(index - 1, "SCY", 0, 3))
                    {
                        index += 1;

                        continue;
                    }
                }

                if ((index + 1) < lastidx)
                {
                    if (encodeMe.regionMatches(index + 1, "IA", 0, 2))
                    {
                        sb.insert(offset, 'X');
                        offset++;
                        index += 1;

                        continue;
                    }
                }

                if (index < lastidx)
                {
                    if (encodeMe.charAt(index + 1) == 'H')
                    {
                        sb.insert(offset, 'X');
                        offset++;
                        index += 1;

                        continue;
                    }

                    if (encodeMe.charAt(index + 1) == 'E')
                    {
                        sb.insert(offset, 'S');
                        offset++;
                        index += 1;

                        continue;
                    }

                    if (encodeMe.charAt(index + 1) == 'I')
                    {
                        sb.insert(offset, 'S');
                        offset++;
                        index += 1;

                        continue;
                    }

                    if (encodeMe.charAt(index + 1) == 'Y')
                    {
                        sb.insert(offset, 'S');
                        offset++;
                        index += 1;

                        continue;
                    }
                }

                sb.insert(offset, 'K');
                offset++;

                continue;
            }

            if (encodeMe.charAt(index) == 'D')
            {
                if ((index + 1) < lastidx)
                {
                    if (encodeMe.regionMatches(index + 1, "GE", 0, 2))
                    {
                        sb.insert(offset, 'J');
                        offset++;
                        index += 2;

                        continue;
                    }

                    if (encodeMe.regionMatches(index + 1, "GI", 0, 2))
                    {
                        sb.insert(offset, 'J');
                        offset++;
                        index += 2;

                        continue;
                    }

                    if (encodeMe.regionMatches(index + 1, "GY", 0, 2))
                    {
                        sb.insert(offset, 'J');
                        offset++;
                        index += 2;

                        continue;
                    }
                }

                sb.insert(offset, 'T');
                offset++;

                continue;
            }

            if (encodeMe.charAt(index) == 'E')
            {
                if (index == 0)
                {
                    sb.insert(offset, encodeMe.charAt(index));
                    offset++;
                }

                continue;
            }

            if (encodeMe.charAt(index) == 'F')
            {
                sb.insert(offset, encodeMe.charAt(index));
                offset++;

                continue;
            }

            if (encodeMe.charAt(index) == 'G')
            {
                if (index < lastidx)
                {
                    if (encodeMe.charAt(index + 1) == 'H')
                    {
                        if ((index + 1) < lastidx)
                        {
                            if (isVowel(encodeMe.charAt(index + 2)))
                            {
                                sb.insert(offset, 'K');
                                index += 1;
                                offset++;

                                continue;
                            }

                            index += 1;

                            continue;
                        }
                    }

                    if (encodeMe.charAt(index + 1) == 'N')
                    {
                        sb.insert(offset, 'N');
                        offset++;
                        index += 1;

                        continue;
                    }

                    if (index > 0)
                    {
                        if (encodeMe.charAt(index - 1) == 'G')
                        {
                            sb.insert(offset, 'K');
                            offset++;

                            continue;
                        }
                    }

                    if (encodeMe.charAt(index + 1) == 'I')
                    {
                        sb.insert(offset, 'J');
                        offset++;

                        continue;
                    }

                    if (encodeMe.charAt(index + 1) == 'E')
                    {
                        sb.insert(offset, 'J');
                        offset++;

                        continue;
                    }

                    if (encodeMe.charAt(index + 1) == 'Y')
                    {
                        sb.insert(offset, 'J');
                        offset++;

                        continue;
                    }

                    if (encodeMe.regionMatches(index - 1, "DGE", 0, 3))
                    {
                        continue;
                    }

                    if (encodeMe.regionMatches(index - 1, "DGI", 0, 3))
                    {
                        continue;
                    }

                    if (encodeMe.regionMatches(index - 1, "DGY", 0, 3))
                    {
                        continue;
                    }
                }

                sb.insert(offset, 'K');
                offset++;

                continue;
            }

            if (encodeMe.charAt(index) == 'H')
            {
                if (index > 0)
                {
                    if (isVowel(encodeMe.charAt(index - 1)))
                    {
                        if (index < lastidx)
                        {
                            if (isVowel(encodeMe.charAt(index + 1)))
                            {
                                sb.insert(offset, encodeMe.charAt(index));
                                offset++;

                                continue;
                            }
                            else
                            {
                                continue;
                            }
                        }
                        else
                        {
                            continue;
                        }
                    }
                }
                else
                {
                    if (!(isVowel(encodeMe.charAt(index + 1))))
                    {
                        continue;
                    }
                }

                sb.insert(offset, encodeMe.charAt(index));
                offset++;

                continue;
            }

            if (encodeMe.charAt(index) == 'I')
            {
                if (index == 0)
                {
                    sb.insert(offset, encodeMe.charAt(index));
                    offset++;
                }

                continue;
            }

            if (encodeMe.charAt(index) == 'J')
            {
                sb.insert(offset, encodeMe.charAt(index));
                offset++;

                continue;
            }

            if (encodeMe.charAt(index) == 'K')
            {
                if (index < lastidx)
                {
                    if (encodeMe.charAt(index + 1) == 'N')
                    {
                        sb.insert(offset, 'N');
                        offset++;
                        index += 1;

                        continue;
                    }
                }

                if (index > 0)
                {
                    if (encodeMe.charAt(index - 1) == 'C')
                    {
                        continue;
                    }
                }

                sb.insert(offset, encodeMe.charAt(index));
                offset++;

                continue;
            }

            if (encodeMe.charAt(index) == 'L')
            {
                sb.insert(offset, encodeMe.charAt(index));
                offset++;

                continue;
            }

            if (encodeMe.charAt(index) == 'M')
            {
                sb.insert(offset, encodeMe.charAt(index));
                offset++;

                continue;
            }

            if (encodeMe.charAt(index) == 'N')
            {
                sb.insert(offset, encodeMe.charAt(index));
                offset++;

                continue;
            }

            if (encodeMe.charAt(index) == 'O')
            {
                if (index == 0)
                {
                    sb.insert(offset, encodeMe.charAt(index));
                    offset++;
                }

                continue;
            }

            if (encodeMe.charAt(index) == 'P')
            {
                if (index < lastidx)
                {
                    if (encodeMe.charAt(index + 1) == 'H')
                    {
                        sb.insert(offset, 'F');
                        offset++;
                        index += 1;

                        continue;
                    }

                    if (encodeMe.charAt(index + 1) == 'N')
                    {
                        sb.insert(offset, 'N');
                        offset++;
                        index += 1;

                        continue;
                    }
                }

                sb.insert(offset, encodeMe.charAt(index));
                offset++;

                continue;
            }

            if (encodeMe.charAt(index) == 'Q')
            {
                sb.insert(offset, 'K');
                offset++;

                continue;
            }

            if (encodeMe.charAt(index) == 'R')
            {
                sb.insert(offset, encodeMe.charAt(index));
                offset++;

                continue;
            }

            if (encodeMe.charAt(index) == 'S')
            {
                if ((index + 2) < lastidx)
                {
                    if (encodeMe.regionMatches(index, "SCHE", 0, 4))
                    {
                        sb.append("SK");
                        offset += 2;
                        index += 2;

                        continue;
                    }

                    if (encodeMe.regionMatches(index, "SCHI", 0, 4))
                    {
                        sb.append("SK");
                        offset += 2;
                        index += 2;

                        continue;
                    }

                    if (encodeMe.regionMatches(index, "SCHO", 0, 4))
                    {
                        sb.append("SK");
                        offset += 2;
                        index += 2;

                        continue;
                    }
                }

                if ((index + 1) < lastidx)
                {
                    if (encodeMe.regionMatches(index, "SCH", 0, 3))
                    {
                        sb.insert(offset, 'X');
                        offset++;
                        index += 2;

                        continue;
                    }

                    if (encodeMe.regionMatches(index + 1, "IA", 0, 2))
                    {
                        sb.insert(offset, 'X');
                        offset++;
                        index += 1;

                        continue;
                    }

                    if (encodeMe.regionMatches(index + 1, "IO", 0, 2))
                    {
                        sb.insert(offset, 'X');
                        offset++;
                        index += 1;

                        continue;
                    }
                }

                if (index < lastidx)
                {
                    if (encodeMe.charAt(index + 1) == 'H')
                    {
                        sb.insert(offset, 'X');
                        offset++;
                        index += 1;

                        continue;
                    }
                }

                sb.insert(offset, encodeMe.charAt(index));
                offset++;

                continue;
            }

            if (encodeMe.charAt(index) == 'T')
            {
                if (index > 0)
                {
                    if ((index + 1) < lastidx)
                    {
                        if (encodeMe.regionMatches(index + 1, "IA", 0, 2))
                        {
                            sb.insert(offset, 'X');
                            offset++;
                            index += 1;

                            continue;
                        }

                        if (encodeMe.regionMatches(index + 1, "IO", 0, 2))
                        {
                            sb.insert(offset, 'X');
                            offset++;
                            index += 1;

                            continue;
                        }
                    }
                }

                if ((index + 1) < lastidx)
                {
                    if (encodeMe.regionMatches(index + 1, "CH", 0, 2))
                    {
                        continue;
                    }
                }

                if (index < lastidx)
                {
                    if (encodeMe.charAt(index + 1) == 'H')
                    {
                        sb.insert(offset, '0');
                        offset++;
                        index += 1;

                        continue;
                    }
                }

                sb.insert(offset, encodeMe.charAt(index));
                offset++;

                continue;
            }

            if (encodeMe.charAt(index) == 'U')
            {
                if (index == 0)
                {
                    sb.insert(offset, encodeMe.charAt(index));
                    offset++;
                }

                continue;
            }

            if (encodeMe.charAt(index) == 'V')
            {
                sb.insert(offset, 'F');
                offset++;

                continue;
            }

            if (encodeMe.charAt(index) == 'W')
            {
                if (index < lastidx)
                {
                    if (encodeMe.charAt(index + 1) == 'R')
                    {
                        sb.insert(offset, 'R');
                        offset++;
                        index += 1;

                        continue;
                    }

                    if (encodeMe.charAt(index + 1) == 'H')
                    {
                        sb.insert(offset, encodeMe.charAt(index));
                        offset++;
                        index += 1;

                        continue;
                    }

                    if (isVowel(encodeMe.charAt(index + 1)))
                    {
                        sb.insert(offset, encodeMe.charAt(index));
                        offset++;

                        continue;
                    }
                }

                continue;
            }

            if (encodeMe.charAt(index) == 'X')
            {
                if (index == 0)
                {
                    sb.insert(offset, 'S');
                    offset++;

                    continue;
                }

                sb.append("KS");
                offset += 2;

                continue;
            }

            if (encodeMe.charAt(index) == 'Y')
            {
                if (index < lastidx)
                {
                    if (isVowel(encodeMe.charAt(index + 1)))
                    {
                        sb.insert(offset, encodeMe.charAt(index));
                        offset++;

                        continue;
                    }
                }

                continue;
            }

            if (encodeMe.charAt(index) == 'Z')
            {
                sb.insert(offset, 'S');
                offset++;
            }
        }

        return sb.toString();
    }

    private static boolean isVowel(char charToTest)
    {
        final String vowels = "AEIOU";

        return vowels.indexOf(charToTest) > MetaPhone.CHAR_NOT_FOUND;
    }
}
