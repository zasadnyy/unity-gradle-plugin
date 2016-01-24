using UnityEngine;
using UnityEditor;
using System.Collections.Generic;
using System;
using LitJson;

namespace Com.Zasadnyy
{
	public class BuildConfig
	{
		public BuildTarget TargetPlatform { get; private set; }

		public string OutputPath { get; private set; }
		public string OutputName { get; private set; }
		public string BundleId { get; private set; }
		public string BundleVersion { get; private set; }
		public bool IsDevelopmentBuild { get; private set; }
		public bool EnableScriptDebugging { get; private set; }
		public string[] Scenes { get; private set; }

		public AndroidConfig Android { get; private set; }

		private BuildConfig()
		{
		}

		public static BuildConfig Parse(string[] args)
		{
			var config = new BuildConfig();
			config.ParseInternal(args);
			return config;
		}

		private void ParseInternal(string[] args)
		{
			var parameters = BuildUtils.ConvertToDictionary(args);

			TargetPlatform = (BuildTarget)Enum.Parse(typeof(BuildTarget), parameters["-TargetPlatform"], true);

			var jsonBuildConfig = JsonMapper.ToObject(parameters["-BuildConfig"]);

			OutputPath = (string)jsonBuildConfig["outputPath"];
			OutputName = (string)jsonBuildConfig["outputName"];
			BundleId = (string)jsonBuildConfig["bundleId"];
			BundleVersion = (string)jsonBuildConfig["bundleVersion"];
			IsDevelopmentBuild = (bool)jsonBuildConfig["isDevelopmentBuild"];
			EnableScriptDebugging = (bool)jsonBuildConfig["enableScriptDebugging"];

			var scenes = new List<String>();
			for(int i = 0; i < jsonBuildConfig["scenes"].Count; i++)
			{
				scenes.Add((string)jsonBuildConfig["scenes"][i]);
			}
			Scenes = scenes.ToArray();

			Android = AndroidConfig.Parse(jsonBuildConfig);

			Debug.Log("parsed build config: " + this);
		}

		public override string ToString()
		{
			return string.Format("[BuildConfig: TargetPlatform={0}, BundleId={1}, BundleVersion={2}, IsDevelopmentBuild={3}, EnableScriptDebugging={4}, Scenes={5}, Android={6}]", TargetPlatform, BundleId, BundleVersion, IsDevelopmentBuild, EnableScriptDebugging, Scenes.Length, Android);
		}

		public class AndroidConfig
		{
			public int BundleVersionCode { get; private set; }
			public bool EnableSplitApplicationBinary { get; private set; }
			public SigningConfig Signing { get; private set; }

			private AndroidConfig()
			{
			}

			public static AndroidConfig Parse(JsonData jsonBuildConfig)
			{
				var config = new AndroidConfig();

				config.BundleVersionCode = (int)jsonBuildConfig["bundleVersionCode"];
				config.EnableSplitApplicationBinary = (bool)jsonBuildConfig["splitApplicationBinary"];

				config.Signing = SigningConfig.Parse(jsonBuildConfig["signingConfig"]);

				return config;
			}

			public override string ToString()
			{
				return string.Format("[AndroidConfig: BundleVersionCode={0}, EnableSplitApplicationBinary={1}, Signing={2}]", BundleVersionCode, EnableSplitApplicationBinary, Signing);
			}

			public class SigningConfig
			{
				public string StorePath { get; private set; }
				public string StorePassword { get; private set; }
				public string KeyAlias { get; private set; }
				public string KeyPassword { get; private set; }

				private SigningConfig ()
				{
				}

				public static SigningConfig Parse(JsonData jsonSigningConfig)
				{
					var config = new SigningConfig();

					config.StorePath = (string)jsonSigningConfig["storePath"];
					config.StorePassword = (string)jsonSigningConfig["storePassword"];
					config.KeyAlias = (string)jsonSigningConfig["keyAlias"];
					config.KeyPassword = (string)jsonSigningConfig["keyPassword"];

					return config;
				}

				public override string ToString ()
				{
					return string.Format ("[SigningConfig: StorePath={0}, StorePassword={1}, KeyAlias={2}, KeyPassword={3}]", StorePath, StorePassword, KeyAlias, KeyPassword);
				}
			}
		}
	}
}