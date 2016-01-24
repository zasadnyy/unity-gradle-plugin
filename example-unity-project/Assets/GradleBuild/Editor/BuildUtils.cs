using System;
using System.Collections.Generic;
using UnityEngine;

namespace Com.Zasadnyy
{
	public static class BuildUtils
	{
		public static Dictionary<string, string> ConvertToDictionary(string[] args)
		{
			var parameters = new Dictionary<string, string>();

			foreach (var arg in args)
			{
				var keyValue = arg.Split ('=');

				if (keyValue.Length == 2)
				{
					parameters [keyValue [0]] = keyValue [1];
				}
				else
				{
					Debug.Log ("BuildConfig.Parse: skip parameter " + arg);
				}
			}
			return parameters;
		}
	}
}

