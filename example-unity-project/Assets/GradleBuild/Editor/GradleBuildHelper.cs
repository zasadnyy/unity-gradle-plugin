using System;
using UnityEditor;
using UnityEngine;
using System.IO;
using LitJson;

namespace Com.Zasadnyy
{
	public static class GradleBuildHelper
	{
		public static void BuildInBatchMode()
		{
			var config = ParseBuildConfig();
			ApplyConfig(config);
			PerformBuild(config);
		}

		static BuildConfig ParseBuildConfig()
		{
			var parameters = BuildUtils.CommandLineArgsToDictionary(Environment.GetCommandLineArgs());
			var serializedBuildConfig = parameters["-BuildConfig"];

			JsonMapper.RegisterImporter<string, BuildTarget>(
				obj => (BuildTarget)Enum.Parse(typeof(BuildTarget), obj)
			);
			JsonMapper.RegisterImporter<string, iOSSdkVersion>(
				obj => (iOSSdkVersion)Enum.Parse(typeof(iOSSdkVersion), obj)
			);
			JsonMapper.RegisterImporter<string, iOSTargetOSVersion>(
				obj => (iOSTargetOSVersion)Enum.Parse(typeof(iOSTargetOSVersion), obj)
			);
			var config = JsonMapper.ToObject<BuildConfig> (serializedBuildConfig);
			JsonMapper.UnregisterImporters();

			return config;
		}

		private static void ApplyConfig(BuildConfig config)
		{
			ApplyCommonConfig(config);

			switch(config.TargetPlatform) {
			case BuildTarget.Android:
				ApplyAdroidConfig(config);
				break;
			case BuildTarget.iOS:
				ApplyIosConfig(config);
				break;
			}
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
			PlayerSettings.Android.keystoreName = config.Android.SigningConfig.StorePath;
			PlayerSettings.Android.keystorePass = config.Android.SigningConfig.StorePassword;
			PlayerSettings.Android.keyaliasName = config.Android.SigningConfig.KeyAlias;
			PlayerSettings.Android.keyaliasPass = config.Android.SigningConfig.KeyPassword;

			PlayerSettings.Android.bundleVersionCode = config.Android.BundleVersionCode;
			PlayerSettings.Android.useAPKExpansionFiles = config.Android.SplitApplicationBinary;

			// TODO make configurable
			EditorUserBuildSettings.androidBuildSubtarget = MobileTextureSubtarget.ETC;
		}

		private static void ApplyIosConfig(BuildConfig config)
		{
			PlayerSettings.iOS.sdkVersion = config.Ios.SdkVersion;
			PlayerSettings.iOS.targetOSVersion = config.Ios.TargetIosVersion;

			// TODO add missing properties
		}

		private static void PerformBuild(BuildConfig config)
		{
			if((config.TargetPlatform != BuildTarget.Android) && (config.TargetPlatform != BuildTarget.iOS))
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
						"GradleBuildHelper: deleting old dist folder failed. Please check if this folder is not used by other applications.");
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
