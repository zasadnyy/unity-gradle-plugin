using System;
using UnityEditor;
using UnityEngine;
using System.IO;

namespace Com.Zasadnyy
{
	public class GradleBuildHelper
	{
		public static void BuildInBatchMode()
		{
			var config = BuildConfig.Parse(Environment.GetCommandLineArgs());
			ApplyConfig(config);
			PerformBuild(config);
		}

		private static void ApplyConfig(BuildConfig config)
		{
			ApplyCommonConfig(config);
			ApplyAdroidConfig(config);
			ApplyIosConfig(config);
		}

		private static void ApplyCommonConfig(BuildConfig config)
		{
			EditorUserBuildSettings.SwitchActiveBuildTarget(config.TargetPlatform);
			
			EditorUserBuildSettings.development = config.IsDevelopmentBuild;
			EditorUserBuildSettings.allowDebugging = config.EnableScriptDebugging;
			PlayerSettings.bundleIdentifier = config.BundleId;
			PlayerSettings.bundleVersion = config.BundleVersion;
		}

		private static void ApplyAdroidConfig(BuildConfig config)
		{
			PlayerSettings.Android.keystoreName = config.Android.Signing.StorePath;
			PlayerSettings.Android.keystorePass = config.Android.Signing.StorePassword;
			PlayerSettings.Android.keyaliasName = config.Android.Signing.KeyAlias;
			PlayerSettings.Android.keyaliasPass = config.Android.Signing.KeyPassword;

			PlayerSettings.Android.bundleVersionCode = config.Android.BundleVersionCode;
			PlayerSettings.Android.useAPKExpansionFiles = config.Android.EnableSplitApplicationBinary;

			// TODO make configurable
			EditorUserBuildSettings.androidBuildSubtarget = AndroidBuildSubtarget.ETC;
		}

		private static void ApplyIosConfig(BuildConfig config)
		{
		}

		private static void PerformBuild(BuildConfig config)
		{
			if((config.TargetPlatform != BuildTarget.Android) && (config.TargetPlatform != BuildTarget.iPhone))
			{
				Debug.LogError("GradleBuildHelper error. Unsupported platform: " + config.TargetPlatform);
				return;
			}

			Debug.Log("GradleBuildHelper: start build " + config.TargetPlatform);

			RecreateDirectory(config.OutputPath);

			var errorMessage = BuildPipeline.BuildPlayer(
				config.Scenes, 
				Path.Combine(config.OutputPath, config.OutputName), 
				config.TargetPlatform, 
				config.IsDevelopmentBuild ? BuildOptions.Development :BuildOptions.None
			);

			OutputBuildResult(errorMessage);
		}

		#region helper methods
		private static void RecreateDirectory(string path)
		{
			if(Directory.Exists(path))
			{
				Debug.Log("GradleBuildHelper: removing old dist folder...");
				try
				{
					Directory.Delete(path, true);
				}
				catch(IOException)
				{
					Debug.LogError(
						"GradleBuildHelper: deleting old dist folder failed. Please check if this folder is not used by other programs.");
				}
			}
			Directory.CreateDirectory(path);
		}

		private static void OutputBuildResult(string errorMessage)
		{
			if(!string.IsNullOrEmpty(errorMessage))
			{
				Debug.LogError("GradleBuildHelper: build failed, error: " + errorMessage);
			}
		}
		#endregion
	}
}
