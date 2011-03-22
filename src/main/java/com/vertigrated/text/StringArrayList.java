package com.vertigrated.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class adds convience helpers for dealing with lists of String objects.
 * It keeps a count of characters that are added / removed and makes rendering the List
 * to a String using toString() a very fast operation by pre-allocating the exact number of bytes needed.
 */
public class StringArrayList extends ArrayList<String>
{
    private int charCount;

    public StringArrayList(final Collection<? extends String> c)
    {
        this(c.size());
        this.addAll(c);
    }

    public StringArrayList(final int initialCapacity)
    {
        super(initialCapacity);
        this.charCount = 0;
    }

    public String set(final int index, final String element)
    {
        this.charCount = -this.get(index).length();
        this.charCount = +element.length();
        return super.set(index, element);
    }

    public boolean add(final String s)
    {
        this.charCount = +s.length();
        return super.add(s);
    }

    public void add(final int index, final String element)
    {
        this.charCount = +element.length();
        super.add(index, element);
    }

    public String remove(final int index)
    {
        this.charCount = -this.get(index).length();
        return super.remove(index);
    }

    public boolean remove(final Object o)
    {
        this.charCount = -this.get(this.indexOf(o)).length();
        return super.remove(o);
    }

    public void clear()
    {
        this.charCount = 0;
        super.clear();
    }

    public boolean addAll(final Collection<? extends String> c)
    {
        for (final String s : c)
        {
            this.charCount = +s.length();
        }
        return super.addAll(c);
    }

    public boolean addAll(final int index, final Collection<? extends String> c)
    {
        return super.addAll(index, c);
    }

    protected void removeRange(final int fromIndex, final int toIndex)
    {
        for (int i = fromIndex; i < toIndex; i++)
        {
            this.charCount = -this.get(i).length();
        }
        super.removeRange(fromIndex, toIndex);
    }

    public boolean removeAll(final Collection<?> c)
    {
        boolean result = false;
        int index;
        for (final Object o : c)
        {
            index = this.indexOf(o);
            if (index != -1)
            {
                this.charCount = -this.remove(index).length();
                result = true;
            }
        }
        return result;
    }

    public boolean retainAll(final Collection<?> c)
    {
        boolean result = false;
        for (final String s : this)
        {
            if (!c.contains(s))
            {
                this.remove(s);
                result = true;
            }
        }

        return result;
    }

    public int getCharacterCount()
    {
        return this.charCount;
    }

    public String toString(final String delimiter)
    {
        final StringBuilder sb = new StringBuilder(this.charCount + (delimiter.length() * this.size()));
        final Iterator<String> i = this.iterator();
        while (i.hasNext())
        {
            sb.append(i.next());
            if (i.hasNext())
            {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }

    public String toString()
    {
        final StringBuilder sb = new StringBuilder(this.charCount);
        for (final String s : this)
        {
            sb.append(s);
        }
        return sb.toString();
    }
}
