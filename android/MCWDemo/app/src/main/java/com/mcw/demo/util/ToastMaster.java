/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mcw.demo.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ToastMaster {
	private static Toast toast;
	private static Context context;

	private ToastMaster() {

	}

	public static void popToast(Context context, String toastText, int during) {
		if (context == null) {
			return;
		}
		if (context instanceof Activity) {
			if (((Activity) context).isFinishing())
				return;
		}
		if (ToastMaster.context == context && toast != null) {
			toast.cancel();
		}
		ToastMaster.context = context;
		toast = Toast.makeText(context, toastText, during);
		toast.show();
	}

	public static void popToast(Context context, int textId, int during) {
		if (context == null) {
			return;
		}
		if (context instanceof Activity) {
			if (((Activity) context).isFinishing())
				return;
		}
		if (ToastMaster.context == context && toast != null) {
			toast.cancel();
		}
		ToastMaster.context = context;
		toast = Toast.makeText(context, textId, during);
		toast.show();
	}


	public static void popToast(Context context, int textId) {
		popToast(context, textId, Toast.LENGTH_SHORT);
	}

	public static void popToast(Context context, String toastText) {
		popToast(context, toastText, Toast.LENGTH_SHORT);
	}
}
