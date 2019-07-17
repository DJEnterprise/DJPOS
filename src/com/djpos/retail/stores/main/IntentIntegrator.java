package com.djpos.retail.stores.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class IntentIntegrator
{
  public static final Collection<String> ALL_CODE_TYPES;
  private static final String BSPLUS_PACKAGE = "com.srowen.bs.android";
  private static final String BS_PACKAGE = "com.google.zxing.client.android";
  public static final Collection<String> DATA_MATRIX_TYPES;
  public static final String DEFAULT_MESSAGE = "This application requires Barcode Scanner. Would you like to install it?";
  public static final String DEFAULT_NO = "No";
  public static final String DEFAULT_TITLE = "Install Barcode Scanner?";
  public static final String DEFAULT_YES = "Yes";
  public static final Collection<String> ONE_D_CODE_TYPES;
  public static final Collection<String> PRODUCT_CODE_TYPES;
  public static final Collection<String> QR_CODE_TYPES;
  public static final int REQUEST_CODE = 49374;
  private static final String TAG = IntentIntegrator.class.getSimpleName();
  public static final Collection<String> TARGET_ALL_KNOWN;
  public static final Collection<String> TARGET_BARCODE_SCANNER_ONLY;
  private final Activity activity;
  private String buttonNo;
  private String buttonYes;
  private String message;
  private final Map<String, Object> moreExtras;
  private Collection<String> targetApplications;
  private String title;

  static
  {
    PRODUCT_CODE_TYPES = list(new String[] { "UPC_A", "UPC_E", "EAN_8", "EAN_13", "RSS_14" });
    ONE_D_CODE_TYPES = list(new String[] { "UPC_A", "UPC_E", "EAN_8", "EAN_13", "CODE_39", "CODE_93", "CODE_128", "ITF", "RSS_14", "RSS_EXPANDED" });
    QR_CODE_TYPES = Collections.singleton("QR_CODE");
    DATA_MATRIX_TYPES = Collections.singleton("DATA_MATRIX");
    ALL_CODE_TYPES = null;
    TARGET_BARCODE_SCANNER_ONLY = Collections.singleton("com.google.zxing.client.android");
    TARGET_ALL_KNOWN = list(new String[] { "com.google.zxing.client.android", "com.srowen.bs.android", "com.srowen.bs.android.simple" });
  }

  public IntentIntegrator(Activity paramActivity)
  {
    this.activity = paramActivity;
    this.title = "Install Barcode Scanner?";
    this.message = "This application requires Barcode Scanner. Would you like to install it?";
    this.buttonYes = "Yes";
    this.buttonNo = "No";
    this.targetApplications = TARGET_ALL_KNOWN;
    this.moreExtras = new HashMap(3);
  }

  private void attachMoreExtras(Intent paramIntent)
  {
    Iterator localIterator = this.moreExtras.entrySet().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str = (String)localEntry.getKey();
      Object localObject = localEntry.getValue();
      if ((localObject instanceof Integer))
      {
        paramIntent.putExtra(str, (Integer)localObject);
        continue;
      }
      if ((localObject instanceof Long))
      {
        paramIntent.putExtra(str, (Long)localObject);
        continue;
      }
      if ((localObject instanceof Boolean))
      {
        paramIntent.putExtra(str, (Boolean)localObject);
        continue;
      }
      if ((localObject instanceof Double))
      {
        paramIntent.putExtra(str, (Double)localObject);
        continue;
      }
      if ((localObject instanceof Float))
      {
        paramIntent.putExtra(str, (Float)localObject);
        continue;
      }
      if ((localObject instanceof Bundle))
      {
        paramIntent.putExtra(str, (Bundle)localObject);
        continue;
      }
      paramIntent.putExtra(str, localObject.toString());
    }
  }

  private String findTargetAppPackage(Intent paramIntent)
  {
    List localList = this.activity.getPackageManager().queryIntentActivities(paramIntent, 65536);
    Iterator localIterator;
  //  if (localList != null)
      localIterator = localList.iterator();
    String str;
    do
    {
      if (!localIterator.hasNext())
        return null;
      str = ((ResolveInfo)localIterator.next()).activityInfo.packageName;
    }
    while (!this.targetApplications.contains(str));
    return str;
  }

  private static Collection<String> list(String[] paramArrayOfString)
  {
    return Collections.unmodifiableCollection(Arrays.asList(paramArrayOfString));
  }

  public static IntentResult parseActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == 114460)
    {
      if (paramInt2 == -1)
      {
        String str1 = paramIntent.getStringExtra("SCAN_RESULT");
        String str2 = paramIntent.getStringExtra("SCAN_RESULT_FORMAT");
        byte[] arrayOfByte = paramIntent.getByteArrayExtra("SCAN_RESULT_BYTES");
        int i = paramIntent.getIntExtra("SCAN_RESULT_ORIENTATION", -2147483648);
        Integer localInteger = null;
        if (i == -2147483648);
        while (true)
        {
          return new IntentResult(str1, str2, arrayOfByte, localInteger, paramIntent.getStringExtra("SCAN_RESULT_ERROR_CORRECTION_LEVEL"));
      //    localInteger = Integer.valueOf(i);
        }
      }
      return new IntentResult();
    }
    return null;
  }

  private AlertDialog showDownloadDialog()
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.activity);
    localBuilder.setTitle(this.title);
    localBuilder.setMessage(this.message);
    localBuilder.setPositiveButton(this.buttonYes, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.google.zxing.client.android"));
        try
        {
          IntentIntegrator.this.activity.startActivity(localIntent);
          return;
        }
        catch (ActivityNotFoundException localActivityNotFoundException)
        {
          Log.w(IntentIntegrator.TAG, "Android Market is not installed; cannot install Barcode Scanner");
        }
      }
    });
    localBuilder.setNegativeButton(this.buttonNo, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
      }
    });
    return localBuilder.show();
  }

  public void addExtra(String paramString, Object paramObject)
  {
    this.moreExtras.put(paramString, paramObject);
  }

  public String getButtonNo()
  {
    return this.buttonNo;
  }

  public String getButtonYes()
  {
    return this.buttonYes;
  }

  public String getMessage()
  {
    return this.message;
  }

  public Map<String, ?> getMoreExtras()
  {
    return this.moreExtras;
  }

  public Collection<String> getTargetApplications()
  {
    return this.targetApplications;
  }

  public String getTitle()
  {
    return this.title;
  }

  public AlertDialog initiateScan()
  {
    return initiateScan(ALL_CODE_TYPES);
  }

  public AlertDialog initiateScan(Collection<String> paramCollection)
  {
    Intent localIntent = new Intent("com.google.zxing.client.android.SCAN");
    localIntent.addCategory("android.intent.category.DEFAULT");
    StringBuilder localStringBuilder;
    Iterator localIterator;
   
      localStringBuilder = new StringBuilder();
     // localIterator = paramCollection.iterator();
    
    String str2;
  
        localIntent.putExtra("SCAN_FORMATS", localStringBuilder.toString());
        str2 = findTargetAppPackage(localIntent);
        if (str2 == null)
          //break;
        return showDownloadDialog();
    
  //    String str1 = (String)localIterator.next();
    /*  if (localStringBuilder.length() > 0)
        localStringBuilder.append(',');
      localStringBuilder.append(str1);*/
    //}
    localIntent.setPackage(str2);
    localIntent.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
    localIntent.addFlags(524288);
    attachMoreExtras(localIntent);
    startActivityForResult(localIntent, 114460);
    return null;
  }

  public void setButtonNo(String paramString)
  {
    this.buttonNo = paramString;
  }

  public void setButtonNoByID(int paramInt)
  {
    this.buttonNo = this.activity.getString(paramInt);
  }

  public void setButtonYes(String paramString)
  {
    this.buttonYes = paramString;
  }

  public void setButtonYesByID(int paramInt)
  {
    this.buttonYes = this.activity.getString(paramInt);
  }

  public void setMessage(String paramString)
  {
    this.message = paramString;
  }

  public void setMessageByID(int paramInt)
  {
    this.message = this.activity.getString(paramInt);
  }

  public void setSingleTargetApplication(String paramString)
  {
    this.targetApplications = Collections.singleton(paramString);
  }

  public void setTargetApplications(Collection<String> paramCollection)
  {
    this.targetApplications = paramCollection;
  }

  public void setTitle(String paramString)
  {
    this.title = paramString;
  }

  public void setTitleByID(int paramInt)
  {
    this.title = this.activity.getString(paramInt);
  }

  public AlertDialog shareText(CharSequence paramCharSequence)
  {
    return shareText(paramCharSequence, "TEXT_TYPE");
  }

  public AlertDialog shareText(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    Intent localIntent = new Intent();
    localIntent.addCategory("android.intent.category.DEFAULT");
    localIntent.setAction("com.google.zxing.client.android.ENCODE");
    localIntent.putExtra("ENCODE_TYPE", paramCharSequence2);
    localIntent.putExtra("ENCODE_DATA", paramCharSequence1);
    String str = findTargetAppPackage(localIntent);
    if (str == null)
      return showDownloadDialog();
    localIntent.setPackage(str);
    localIntent.addFlags(67108864);
    localIntent.addFlags(524288);
    attachMoreExtras(localIntent);
    this.activity.startActivity(localIntent);
    return null;
  }

  protected void startActivityForResult(Intent paramIntent, int paramInt)
  {
    this.activity.startActivityForResult(paramIntent, paramInt);
  }
  
 
}

