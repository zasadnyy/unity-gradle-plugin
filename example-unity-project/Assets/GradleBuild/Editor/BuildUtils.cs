using System;
using System.Collections.Generic;
using UnityEngine;

namespace Com.Zasadnyy
{
	public static class BuildUtils
	{
		public static Dictionary<string, string> CommandLineArgsToDictionary(string[] args)
		{
			var parameters = new Dictionary<string, string>();

			foreach (var arg in args)
			{
				// we care only about splitting the first '=' character,
				// because of possible passwords including '=' characters
				var keyValue = arg.Split (new char[] {'='}, 2);

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

