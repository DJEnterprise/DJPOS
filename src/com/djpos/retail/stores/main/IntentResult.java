package com.djpos.retail.stores.main;

public final class IntentResult
{
  private final String contents;
  private final String errorCorrectionLevel;
  private final String formatName;
  private final Integer orientation;
  private final byte[] rawBytes;

  IntentResult()
  {
    this(null, null, null, null, null);
  }

  IntentResult(String paramString1, String paramString2, byte[] paramArrayOfByte, Integer paramInteger, String paramString3)
  {
    this.contents = paramString1;
    this.formatName = paramString2;
    this.rawBytes = paramArrayOfByte;
    this.orientation = paramInteger;
    this.errorCorrectionLevel = paramString3;
  }

  public String getContents()
  {
    return this.contents;
  }

  public String getErrorCorrectionLevel()
  {
    return this.errorCorrectionLevel;
  }

  public String getFormatName()
  {
    return this.formatName;
  }

  public Integer getOrientation()
  {
    return this.orientation;
  }

  public byte[] getRawBytes()
  {
    return this.rawBytes;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(100);
    localStringBuilder.append("Format: ").append(this.formatName).append('\n');
    localStringBuilder.append("Contents: ").append(this.contents).append('\n');
    if (this.rawBytes == null);
    for (int i = 0; ; i = this.rawBytes.length)
    {
      localStringBuilder.append("Raw bytes: (").append(i).append(" bytes)\n");
      localStringBuilder.append("Orientation: ").append(this.orientation).append('\n');
      localStringBuilder.append("EC level: ").append(this.errorCorrectionLevel).append('\n');
      return localStringBuilder.toString();
    }
  }
}

/* Location:           F:\ANDROID\Enders\classes_dex2jar.jar
 * Qualified Name:     enders.pos.test.IntentResult
 * JD-Core Version:    0.6.0
 */