package li.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

/**
 * 
 * Dialog
 * 
 * @author LD
 *
 * ����03:34:10  2012-6-8
 */
public class AlertDialogUtils {

	public static void deleteChooseDialog(Activity context,OnClickListener clickListener){
		AlertDialog alertDialog = new AlertDialog.Builder(context)
		.setIcon(android.R.drawable.ic_menu_help)
		.setTitle("ȷ��Ҫɾ����ͼƬ��")
		.setPositiveButton("ȷ��", clickListener)
		.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).create();
		alertDialog.show();
	}

	public static void showDialog(Context context, int statSysWarning,
			String title, String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(context)
		.setIcon(statSysWarning)
		.setTitle(title)
		.setMessage(message)
		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).create();
		alertDialog.show();
	}
}
