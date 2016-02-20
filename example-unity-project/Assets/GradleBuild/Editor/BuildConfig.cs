using UnityEditor;

namespace Com.Zasadnyy
{
	public class SigningConfig
	{
		public string StorePath { get; set; }
		public string StorePassword { get; set; }
		public string KeyAlias { get; set; }
		public string KeyPassword { get; set; }
	}

	public class Android
	{
		public bool SplitApplicationBinary { get; set; }
		public int BundleVersionCode { get; set; }
		public SigningConfig SigningConfig { get; set; }
	}

	public class Ios
	{
//		public string ShortBundleVersion { get; set; } TODO: find coresponding property
//		public  scriptingBackend
		public iOSSdkVersion SdkVersion { get; set; }
		public iOSTargetOSVersion TargetIosVersion { get; set; }
	}

	public class BuildConfig
	{
		public BuildTarget TargetPlatform { get; set; }

		public string OutputPath { get; set; }
		public string OutputName { get; set; }
		public string LogFile { get; set; }
		public string BundleId { get; set; }
		public string BundleVersion { get; set; }
		public bool IsDevelopmentBuild { get; set; }
		public bool EnableScriptDebugging { get; set; }
		public string[] Scenes { get; set; }

		public Android Android { get; set; }

		public Ios Ios { get; set; }
	}
}