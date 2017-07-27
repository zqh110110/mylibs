package com.kfd.ui;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.kfd.common.Logcat;
import com.longevitysoft.android.xml.plist.PListXMLHandler;
import com.longevitysoft.android.xml.plist.PListXMLParser;
import com.longevitysoft.android.xml.plist.domain.Array;
import com.longevitysoft.android.xml.plist.domain.PList;


public class EmojiUtils {
	
	private static final String TAG = "EmojiUtils";
	private static final String EMOJI_PLIST = "ios6EmojiList_990.plist";
	
	private EmojiUtils(){}
	
	private final static String[] SMAIL_1 = new String[] { "ef0000ef",
			"ef0002ef", "ef0467ef", "ef0001ef",
			"ef0003ef", "ef0004ef", "ef0005ef",
			"ef0006ef", "ef0007ef", "ef0469ef",
			"ef0470ef", "ef0011ef", "ef0012ef",
			"ef0471ef", "ef0008ef", "ef0010ef",
			"ef0016ef", "ef0009ef", "ef0013ef",
			"ef0017ef", "ef0022ef"  };
	
	private final static String[] SMAIL_2 = new String[] { "ef0023ef",
			"ef0025ef", "ef0024ef", "ef0030ef",
			"ef0019ef", "ef0020ef", "ef0472ef",
			"ef0015ef", "ef0473ef", "ef0474ef",
			"ef0021ef", "ef0027ef", "ef0028ef",
			"ef0029ef", "ef0475ef", "ef0018ef",
			"ef0476ef", "ef0477ef", "ef0031ef",
			"ef0478ef", "ef0479ef"};
	
	private final static String[] SMAIL_3 = new String[] { "ef0480ef",
			"ef0026ef", "ef0481ef", "ef0482ef",
			"ef0483ef", "ef0484ef", "ef0032ef",
			"ef0485ef", "ef0486ef", "ef0487ef",
			"ef0488ef", "ef0489ef", "ef0490ef",
			"ef0491ef", "ef0014ef", "ef0492ef",
			"ef0095ef", "ef0096ef", "ef0098ef",
			"ef0097ef", "ef0101ef"};
	
	private final static String[] SMAIL_4 = new String[] { "ef0091ef",
			"ef0087ef", "ef0088ef", "ef0090ef",
			"ef0089ef", "ef0093ef", "ef0092ef",
			"ef0094ef", "ef0099ef", "ef0100ef",
			"ef0493ef", "ef0494ef", "ef0495ef",
			"ef0496ef", "ef0497ef", "ef0498ef",
			"ef0499ef", "ef0500ef", "ef0501ef",
			"ef0502ef", "ef0503ef" };
	
	private final static String[] SMAIL_5 = new String[] { "ef0504ef",
			"ef0505ef", "ef0506ef", "ef0102ef",
			"ef0033ef", "ef0054ef", "ef0053ef",
			"ef0043ef", "ef0044ef", "ef0508ef",
			"ef0509ef", "ef0045ef", "ef0050ef",
			"ef0510ef", "ef0048ef", "ef0049ef",
			"ef0106ef", "ef0107ef", "ef0108ef",
			"ef0511ef", "ef0105ef" };
	
	private final static String[] SMAIL_6 = new String[] { "ef0055ef",
			"ef0056ef", "ef0057ef", "ef0058ef",
			"ef0059ef", "ef0060ef", "ef0061ef",
			"ef0062ef", "ef0063ef", "ef0064ef",
			"ef0065ef", "ef0066ef", "ef0067ef",
			"ef0068ef", "ef0069ef", "ef0070ef",
			"ef0071ef", "ef0072ef", "ef0073ef",
			"ef0074ef", "ef0076ef" };
	
	private final static String[] SMAIL_7 = new String[] { "ef0075ef",
			"ef0516ef", "ef0517ef", "ef0518ef",
			"ef0082ef", "ef0083ef", "ef0077ef",
			"ef0078ef", "ef0079ef", "ef0080ef",
			"ef0519ef", "ef0084ef", "ef0085ef",
			"ef0086ef", "ef0520ef", "ef0521ef",
			"ef0522ef", "ef0081ef", "ef0203ef",
			"ef0204ef", "ef0205ef" };
	
	private final static String[] SMAIL_8 = new String[] { "ef0193ef",
			"ef0523ef", "ef0194ef", "ef0195ef",
			"ef0196ef", "ef0197ef", "ef0198ef",
			"ef0524ef", "ef0199ef", "ef0525ef",
			"ef0526ef", "ef0200ef", "ef0201ef",
			"ef0207ef", "ef0208ef", "ef0527ef",
			"ef0528ef", "ef0529ef", "ef0202ef",
			"ef0206ef", "ef0209ef" };
	
	private final static String[] SMAIL_9 = new String[] { "ef0034ef",
			"ef0035ef", "ef0036ef", "ef0038ef",
			"ef0039ef", "ef0040ef", "ef0037ef",
			"ef0041ef", "ef0531ef", "ef0532ef",
			"ef0533ef", "ef0042ef", "ef0534ef",
			"ef0104ef", "ef0210ef", "ef0211ef",
			"ef0535ef", "ef0536ef", "ef0537ef",
			"ef0103ef", "ef0538ef" };
	
	//flower
	private final static String[] SMAIL_10 = new String[] { "ef0253ef",
			"ef0257ef", "ef0252ef", "ef0254ef",
			"ef0255ef", "ef0256ef", "ef0258ef",
			"ef0259ef", "ef0260ef", "ef0261ef",
			"ef0262ef", "ef0539ef", "ef0263ef",
			"ef0264ef", "ef0265ef", "ef0266ef",
			"ef0267ef", "ef0270ef", "ef0271ef",
			"ef0540ef", "ef0276ef" };
	
	private final static String[] SMAIL_11 = new String[] { "ef0273ef",
			"ef0274ef", "ef0541ef", "ef0542ef",
			"ef0275ef", "ef0272ef", "ef0543ef",
			"ef0277ef", "ef0544ef", "ef0545ef",
			"ef0546ef", "ef0547ef", "ef0278ef",
			"ef0296ef", "ef0279ef", "ef0280ef",
			"ef0282ef", "ef0281ef", "ef0548ef",
			"ef0549ef", "ef0550ef" };
	
	private final static String[] SMAIL_12 = new String[] { "ef0551ef",
			"ef0552ef", "ef0553ef", "ef0554ef",
			"ef0555ef", "ef0268ef", "ef0556ef",
			"ef0557ef", "ef0558ef", "ef0559ef",
			"ef0560ef", "ef0561ef", "ef0562ef",
			"ef0563ef", "ef0564ef", "ef0269ef",
			"ef0565ef", "ef0566ef", "ef0567ef",
			"ef0568ef", "ef0569ef" };
	
	private final static String[] SMAIL_13 = new String[] { "ef0283ef",
			"ef0284ef", "ef0285ef", "ef0286ef",
			"ef0287ef", "ef0288ef", "ef0289ef",
			"ef0290ef", "ef0291ef", "ef0292ef",
			"ef0570ef", "ef0295ef", "ef0571ef",
			"ef0294ef", "ef0293ef", "ef0572ef",
			"ef0573ef", "ef0574ef", "ef0575ef",
			"ef0576ef", "ef0577ef" };
	
	private final static String[] SMAIL_14 = new String[] { "ef0578ef",
			"ef0579ef", "ef0580ef", "ef0581ef",
			"ef0582ef", "ef0583ef", "ef0584ef",
			"ef0585ef", "ef0586ef", "ef0587ef",
			"ef0588ef", "ef0589ef", "ef0590ef",
			"ef0248ef", "ef0591ef", "ef0592ef",
			"ef0593ef", "ef0594ef", "ef0595ef",
			"ef0596ef", "ef0943ef" };
	
	private final static String[] SMAIL_15 = new String[] { "ef0244ef",
			"ef0944ef", "ef0246ef", "ef0249ef",
			"ef0245ef", "ef0945ef", "ef0247ef",
			"ef0250ef", "ef0605ef", "ef0423ef",
			"ef0251ef" };
	
	//第三类
	private final static String[] SMAIL_16 = new String[] { "ef0109ef",
			"ef0110ef", "ef0111ef", "ef0112ef",
			"ef0113ef", "ef0114ef", "ef0115ef",
			"ef0116ef", "ef0117ef", "ef0118ef",
			"ef0119ef", "ef0120ef", "ef0121ef",
			"ef0122ef", "ef0123ef", "ef0606ef",
			"ef0125ef", "ef0607ef", "ef0126ef",
			"ef0456ef", "ef0608ef" };
	
	private final static String[] SMAIL_17 = new String[] { "ef0130ef",
			"ef0129ef", "ef0609ef", "ef0137ef",
			"ef0127ef", "ef0128ef", "ef0136ef",
			"ef0610ef", "ef0131ef", "ef0133ef",
			"ef0135ef", "ef0612ef", "ef0613ef",
			"ef0134ef", "ef0142ef", "ef0132ef",
			"ef0141ef", "ef0138ef", "ef0614ef",
			"ef0615ef", "ef0616ef" };
	
	private final static String[] SMAIL_18 = new String[] { "ef0124ef",
			"ef0617ef", "ef0139ef", "ef0140ef",
			"ef0946ef", "ef0947ef", "ef0948ef",
			"ef0949ef", "ef0145ef", "ef0146ef",
			"ef0622ef", "ef0623ef", "ef0147ef",
			"ef0624ef", "ef0150ef", "ef0625ef",
			"ef0626ef", "ef0627ef", "ef0628ef",
			"ef0629ef", "ef0144ef" };
	
	private final static String[] SMAIL_19 = new String[] { "ef0630ef",
			"ef0155ef", "ef0631ef", "ef0156ef",
			"ef0632ef", "ef0633ef", "ef0149ef",
			"ef0634ef", "ef0160ef", "ef0161ef",
			"ef0162ef", "ef0635ef", "ef0163ef",
			"ef0164ef", "ef0158ef", "ef0636ef",
			"ef0637ef", "ef0638ef", "ef0639ef",
			"ef0640ef", "ef0641ef" };
	
	private final static String[] SMAIL_20 = new String[] { "ef0151ef",
			"ef0642ef", "ef0643ef", "ef0644ef",
			"ef0950ef", "ef0152ef", "ef0646ef",
			"ef0647ef", "ef0153ef", "ef0648ef",
			"ef0649ef", "ef0650ef", "ef0154ef",
			"ef0651ef", "ef0184ef", "ef0652ef",
			"ef0653ef", "ef0654ef", "ef0655ef",
			"ef0656ef", "ef0657ef" };
	
	private final static String[] SMAIL_21 = new String[] { "ef0658ef",
			"ef0659ef", "ef0660ef", "ef0661ef",
			"ef0662ef", "ef0663ef", "ef0664ef",
			"ef0148ef", "ef0666ef", "ef0667ef",
			"ef0951ef", "ef0952ef", "ef0670ef",
			"ef0671ef", "ef0672ef", "ef0673ef",
			"ef0674ef", "ef0675ef", "ef0676ef",
			"ef0677ef", "ef0678ef" };
	
	private final static String[] SMAIL_22 = new String[] { "ef0679ef",
			"ef0185ef", "ef0680ef", "ef0681ef",
			"ef0682ef", "ef0683ef", "ef0684ef",
			"ef0186ef", "ef0183ef", "ef0187ef",
			"ef0188ef", "ef0685ef", "ef0052ef",
			"ef0051ef", "ef0686ef", "ef0687ef",
			"ef0189ef", "ef0190ef", "ef0191ef",
			"ef0180ef", "ef0688ef" };
	
	private final static String[] SMAIL_23 = new String[] { "ef0689ef",
			"ef0690ef", "ef0182ef", "ef0691ef",
			"ef0181ef", "ef0165ef", "ef0166ef",
			"ef0167ef", "ef0168ef", "ef0169ef",
			"ef0171ef", "ef0694ef", "ef0695ef",
			"ef0170ef", "ef0697ef", "ef0698ef",
			"ef0455ef", "ef0699ef", "ef0179ef",
			"ef0174ef", "ef0700ef" };
	
	private final static String[] SMAIL_24 = new String[] { "ef0172ef",
			"ef0173ef", "ef0701ef", "ef0212ef",
			"ef0213ef", "ef0217ef", "ef0703ef",
			"ef0214ef", "ef0215ef", "ef0216ef",
			"ef0704ef", "ef0705ef", "ef0218ef",
			"ef0706ef", "ef0219ef", "ef0220ef",
			"ef0707ef", "ef0708ef", "ef0221ef",
			"ef0222ef", "ef0709ef" };
	
	private final static String[] SMAIL_25 = new String[] { "ef0223ef",
			"ef0224ef", "ef0710ef", "ef0225ef",
			"ef0226ef", "ef0227ef", "ef0228ef",
			"ef0229ef", "ef0232ef", "ef0233ef",
			"ef0231ef", "ef0230ef", "ef0711ef",
			"ef0712ef", "ef0234ef", "ef0713ef",
			"ef0235ef", "ef0236ef", "ef0237ef",
			"ef0714ef", "ef0715ef" };
	
	private final static String[] SMAIL_26 = new String[] { "ef0716ef",
			"ef0717ef", "ef0718ef", "ef0238ef",
			"ef0719ef", "ef0239ef", "ef0720ef",
			"ef0721ef", "ef0722ef", "ef0240ef",
			"ef0241ef", "ef0723ef", "ef0724ef",
			"ef0725ef", "ef0726ef", "ef0727ef",
			"ef0728ef", "ef0242ef", "ef0243ef",
			"ef0729ef" };
	
	//第四类
	private final static String[] SMAIL_27 = new String[] { "ef0398ef",
			"ef0730ef", "ef0399ef", "ef0400ef",
			"ef0401ef", "ef0402ef", "ef0403ef",
			"ef0404ef", "ef0405ef", "ef0406ef",
			"ef0407ef", "ef0408ef", "ef0409ef",
			"ef0732ef", "ef0410ef", "ef0411ef",
			"ef0413ef", "ef0414ef", "ef0415ef",
			"ef0416ef", "ef0417ef" };
	
	private final static String[] SMAIL_28 = new String[] { "ef0734ef",
			"ef0418ef", "ef0419ef", "ef0420ef",
			"ef0421ef", "ef0422ef", "ef0735ef",
			"ef0736ef", "ef0424ef", "ef0425ef",
			"ef0426ef", "ef0427ef", "ef0429ef",
			"ef0428ef", "ef0739ef", "ef0953ef",
			"ef0431ef", "ef0430ef", "ef0157ef",
			"ef0742ef", "ef0743ef" };
	
	private final static String[] SMAIL_29 = new String[] { "ef0744ef",
			"ef0442ef", "ef0989ef", "ef0746ef",
			"ef0443ef", "ef0444ef", "ef0747ef",
			"ef0346ef", "ef0748ef", "ef0749ef",
			"ef0441ef", "ef0745ef", "ef0436ef",
			"ef0751ef", "ef0433ef", "ef0752ef",
			"ef0434ef", "ef0435ef", "ef0753ef",
			"ef0754ef", "ef0440ef" };
	
	private final static String[] SMAIL_30 = new String[] { "ef0755ef",
			"ef0437ef", "ef0756ef", "ef0438ef",
			"ef0439ef", "ef0757ef", "ef0432ef",
			"ef0758ef", "ef0759ef", "ef0760ef",
			"ef0761ef", "ef0453ef", "ef0452ef",
			"ef0445ef", "ef0762ef", "ef0447ef",
			"ef0448ef", "ef0449ef", "ef0450ef",
			"ef0446ef", "ef0765ef" };
	
	private final static String[] SMAIL_31 = new String[] { "ef0451ef",
			"ef0454ef", "ef0767ef", "ef0768ef",
			"ef0769ef", "ef0770ef", "ef0771ef",
			"ef0457ef", "ef0458ef", "ef0466ef",
			"ef0459ef", "ef0460ef", "ef0461ef",
			"ef0462ef", "ef0463ef", "ef0464ef",
			"ef0465ef"
	};
	
	//number
	private final static String[] SMAIL_32 = new String[] { "ef0297ef",
			"ef0298ef", "ef0299ef", "ef0300ef",
			"ef0301ef", "ef0302ef", "ef0303ef",
			"ef0304ef", "ef0305ef", "ef0306ef",
			"ef0802ef", "ef0803ef", "ef0307ef",
			"ef0805ef", "ef0308ef", "ef0309ef",
			"ef0310ef", "ef0311ef", "ef0810ef",
			"ef0811ef", "ef0812ef" };
	
	private final static String[] SMAIL_33 = new String[] { "ef0312ef",
			"ef0313ef", "ef0314ef", "ef0315ef",
			"ef0954ef", "ef0955ef", "ef0819ef",
			"ef0316ef", "ef0317ef", "ef0822ef",
			"ef0823ef", "ef0956ef", "ef0957ef",
			"ef0958ef", "ef0318ef", "ef0319ef",
			"ef0959ef", "ef0960ef", "ef0961ef",
			"ef0962ef", "ef0320ef"
			};
	
	private final static String[] SMAIL_34 = new String[]{
			"ef0833ef",
			"ef0834ef", "ef0835ef", "ef0321ef",
			"ef0323ef", "ef0324ef", "ef0836ef",
			"ef0837ef", "ef0327ef", "ef0325ef",
			"ef0326ef", "ef0332ef", "ef0329ef",
			"ef0328ef", "ef0838ef", "ef0839ef",
			"ef0330ef", "ef0331ef", "ef0333ef",
			"ef0334ef", "ef0335ef"
			};
	
	private final static String[] SMAIL_35 = new String[]{
			"ef0339ef",
			"ef0340ef", "ef0341ef", "ef0342ef",
			"ef0347ef", "ef0840ef", "ef0841ef",
			"ef0344ef", "ef0345ef", "ef0343ef",
			"ef0336ef", "ef0337ef", "ef0338ef",
			"ef0963ef", "ef0844ef", "ef0845ef",
			"ef0846ef", "ef0847ef", "ef0848ef",
			"ef0348ef", "ef0349ef"
			};
	
	private final static String[] SMAIL_36 = new String[]{
			"ef0851ef",
			"ef0852ef", "ef0351ef", "ef0853ef",
			"ef0350ef", "ef0854ef", "ef0855ef",
			"ef0856ef", "ef0857ef", "ef0858ef",
			"ef0859ef", "ef0964ef", "ef0352ef",
			"ef0965ef", "ef0966ef", "ef0967ef",
			"ef0353ef", "ef0354ef", "ef0355ef",
			"ef0356ef", "ef0357ef"
	};
	
	private final static String[] SMAIL_37 = new String[]{
			"ef0374ef",
			"ef0375ef", "ef0376ef", "ef0377ef",
			"ef0866ef", "ef0143ef", "ef0968ef",
			"ef0360ef", "ef0361ef", "ef0362ef",
			"ef0363ef", "ef0364ef", "ef0365ef",
			"ef0366ef", "ef0367ef", "ef0368ef",
			"ef0369ef", "ef0370ef", "ef0371ef",
			"ef0372ef", "ef0373ef"
	};
	
	private final static String[] SMAIL_38 = new String[] { "ef0412ef",
			"ef0358ef", "ef0882ef", "ef0359ef",
			"ef0395ef", "ef0396ef", "ef0397ef",
			"ef0192ef", "ef0969ef", "ef0322ef",
			"ef0892ef", "ef0893ef", "ef0894ef",
			"ef0895ef", "ef0394ef", "ef0393ef",
			"ef0970ef", "ef0971ef", "ef0046ef",
			"ef0047ef", "ef0896ef" };
	
	private final static String[] SMAIL_39 = new String[] { "ef0381ef",
			"ef0897ef", "ef0382ef", "ef0898ef",
			"ef0383ef", "ef0899ef", "ef0384ef",
			"ef0900ef", "ef0385ef", "ef0901ef",
			"ef0386ef", "ef0902ef", "ef0387ef",
			"ef0388ef", "ef0389ef", "ef0390ef",
			"ef0391ef", "ef0392ef", "ef0903ef",
			"ef0904ef", "ef0905ef" };
	
	private final static String[] SMAIL_40 = new String[] { "ef0906ef",
			"ef0907ef", "ef0908ef", "ef0972ef",
			"ef0973ef", "ef0974ef", "ef0975ef",
			"ef0175ef", "ef0176ef", "ef0177ef",
			"ef0178ef", "ef0917ef", "ef0918ef",
			"ef0976ef", "ef0977ef", "ef0921ef",
			"ef0922ef", "ef0978ef", "ef0159ef",
			"ef0378ef", "ef0380ef" };
	
	private final static String[] SMAIL_41 = new String[] { "ef0979ef",
			"ef0980ef", "ef0981ef", "ef0982ef",
			"ef0983ef", "ef0984ef", "ef0932ef",
			"ef0985ef", "ef0986ef", "ef0987ef",
			"ef0988ef", "ef0379ef", "ef0935ef",
			"ef0936ef", "ef0939ef", "ef0940ef",
			"ef0941ef", "ef0942ef" };

	public static String[] getSmail(int position) {
		String[] smail = new String[]{};
		switch (position) {
		case 0:
			smail = SMAIL_1;
			break;
		case 1:
			smail = SMAIL_2;
			break;
		case 2:
			smail = SMAIL_3;
			break;
		case 3:
			smail = SMAIL_4;
			break;
		case 4:
			smail = SMAIL_5;
			break;
		case 5:
			smail = SMAIL_6;
			break;
		case 6:
			smail = SMAIL_7;
			break;
		case 7:
			smail = SMAIL_8;
			break;
		case 8:
			smail = SMAIL_9;
			break;
		default:
			break;
		}
		return smail;
	}
	
	
	public static String[] getflowers(int position)
	{
		String[] smail = new String[]{};
		switch (position) {
		case 0:
			smail = SMAIL_10;
			break;
		case 1:
			smail = SMAIL_11;
			break;
		case 2:
			smail = SMAIL_12;
			break;
		case 3:
			smail = SMAIL_13;
			break;
		case 4:
			smail = SMAIL_14;
			break;
		case 5:
			smail = SMAIL_15;
			break;
		default:
			break;
		}
		return smail;
	}
	
	public static String[] getBells(int position)
	{
		String[] smail = new String[] {};
		switch (position)
		{
			case 0:
				smail = EmojiUtils.SMAIL_16;
				break;
			case 1:
				smail = EmojiUtils.SMAIL_17;
				break;
			case 2:
				smail = EmojiUtils.SMAIL_18;
				break;
			case 3:
				smail = EmojiUtils.SMAIL_19;
				break;
			case 4:
				smail = EmojiUtils.SMAIL_20;
				break;
			case 5:
				smail = EmojiUtils.SMAIL_21;
				break;
			case 6:
				smail = EmojiUtils.SMAIL_22;
				break;
			case 7:
				smail = EmojiUtils.SMAIL_23;
				break;
			case 8:
				smail = EmojiUtils.SMAIL_24;
				break;
			case 9:
				smail = EmojiUtils.SMAIL_25;
				break;
			case 10:
				smail = EmojiUtils.SMAIL_26;
				break;
			default:
				break;
		}
		return smail;
	}
	
	public static String[] getNumbers(int position)
	{
		String[] smail = new String[] {};
		switch (position)
		{
			case 0:
				smail = EmojiUtils.SMAIL_32;
				break;
			case 1:
				smail = EmojiUtils.SMAIL_33;
				break;
			case 2:
				smail = EmojiUtils.SMAIL_34;
				break;
			case 3:
				smail = EmojiUtils.SMAIL_35;
				break;
			case 4:
				smail = EmojiUtils.SMAIL_36;
				break;
			case 5:
				smail = EmojiUtils.SMAIL_37;
				break;
			case 6:
				smail = EmojiUtils.SMAIL_38;
				break;
			case 7:
				smail = EmojiUtils.SMAIL_39;
				break;
			case 8:
				smail = EmojiUtils.SMAIL_40;
				break;
			case 9:
				smail = EmojiUtils.SMAIL_41;
				break;
			default:
				break;
		}
		return smail;
	}

	public static String[] getCars(int position)
	{
		String[] smail = new String[] {};
		switch (position)
		{
			case 0:
				smail = EmojiUtils.SMAIL_27;
				break;
			case 1:
				smail = EmojiUtils.SMAIL_28;
				break;
			case 2:
				smail = EmojiUtils.SMAIL_29;
				break;
			case 3:
				smail = EmojiUtils.SMAIL_30;
				break;
			case 4:
				smail = EmojiUtils.SMAIL_31;
				break;
			default:
				break;
		}
		return smail;
	}
	
	private static String[] file_name = { "ef0001ef", "ef0001ef", "ef0002ef",
			"ef0003ef", "ef0004ef", "ef0005ef", "ef0006ef", "ef0007ef",
			"ef0008ef", "ef0009ef", "ef0010ef", "ef0011ef", "ef0012ef",
			"ef0013ef", "ef0014ef", "ef0015ef", "ef0016ef", "ef0017ef",
			"ef0018ef", "ef0019ef", "ef0020ef", "ef0021ef", "ef0022ef",
			"ef0023ef", "ef0024ef", "ef0025ef", "ef0026ef", "ef0027ef",
			"ef0028ef", "ef0029ef", "ef0030ef", "ef0031ef", "ef0032ef",
			"ef0033ef", "ef0034ef", "ef0035ef", "ef0036ef", "ef0037ef",
			"ef0038ef", "ef0039ef", "ef0040ef", "ef0041ef", "ef0042ef",
			"ef0043ef", "ef0044ef", "ef0045ef", "ef0046ef", "ef0047ef",
			"ef0048ef", "ef0049ef", "ef0050ef", "ef0051ef", "ef0052ef",
			"ef0053ef", "ef0054ef", "ef0055ef", "ef0056ef", "ef0057ef",
			"ef0058ef", "ef0059ef", "ef0060ef", "ef0061ef", "ef0062ef",
			"ef0063ef", "ef0064ef", "ef0065ef", "ef0066ef", "ef0067ef",
			"ef0068ef", "ef0069ef", "ef0070ef", "ef0071ef", "ef0072ef",
			"ef0073ef", "ef0074ef", "ef0075ef", "ef0076ef", "ef0077ef",
			"ef0078ef", "ef0079ef", "ef0080ef", "ef0081ef", "ef0082ef",
			"ef0083ef", "ef0084ef", "ef0085ef", "ef0086ef", "ef0087ef",
			"ef0088ef", "ef0089ef", "ef0090ef", "ef0091ef", "ef0092ef",
			"ef0093ef", "ef0094ef", "ef0095ef", "ef0096ef", "ef0097ef",
			"ef0098ef", "ef0099ef", "ef0100ef", "ef0101ef", "ef0102ef",
			"ef0103ef", "ef0104ef", "ef0105ef", "ef0106ef", "ef0107ef",
			"ef0108ef", "ef0109ef", "ef0110ef", "ef0111ef", "ef0112ef",
			"ef0113ef", "ef0114ef", "ef0115ef", "ef0116ef", "ef0117ef",
			"ef0118ef", "ef0119ef", "ef0120ef", "ef0121ef", "ef0122ef",
			"ef0123ef", "ef0124ef", "ef0125ef", "ef0126ef", "ef0127ef",
			"ef0128ef", "ef0129ef", "ef0130ef", "ef0131ef", "ef0132ef",
			"ef0133ef", "ef0134ef", "ef0135ef", "ef0136ef", "ef0137ef",
			"ef0138ef", "ef0139ef", "ef0140ef", "ef0141ef", "ef0142ef",
			"ef0143ef", "ef0144ef", "ef0145ef", "ef0146ef", "ef0147ef",
			"ef0148ef", "ef0149ef", "ef0150ef", "ef0151ef", "ef0152ef",
			"ef0153ef", "ef0154ef", "ef0155ef", "ef0156ef", "ef0157ef",
			"ef0158ef", "ef0159ef", "ef0160ef", "ef0161ef", "ef0162ef",
			"ef0163ef", "ef0164ef", "ef0165ef", "ef0166ef", "ef0167ef",
			"ef0168ef", "ef0169ef", "ef0170ef", "ef0171ef", "ef0172ef",
			"ef0173ef", "ef0174ef", "ef0175ef", "ef0176ef", "ef0177ef",
			"ef0178ef", "ef0179ef", "ef0180ef", "ef0181ef", "ef0182ef",
			"ef0183ef", "ef0184ef", "ef0185ef", "ef0186ef", "ef0187ef",
			"ef0188ef", "ef0189ef", "ef0190ef", "ef0191ef", "ef0192ef",
			"ef0193ef", "ef0194ef", "ef0195ef", "ef0196ef", "ef0197ef",
			"ef0198ef", "ef0199ef", "ef0200ef", "ef0201ef", "ef0202ef",
			"ef0203ef", "ef0204ef", "ef0205ef", "ef0206ef", "ef0207ef",
			"ef0208ef", "ef0209ef", "ef0210ef", "ef0211ef", "ef0212ef",
			"ef0213ef", "ef0214ef", "ef0215ef", "ef0216ef", "ef0217ef",
			"ef0218ef", "ef0219ef", "ef0220ef", "ef0221ef", "ef0222ef",
			"ef0223ef", "ef0224ef", "ef0225ef", "ef0226ef", "ef0227ef",
			"ef0228ef", "ef0229ef", "ef0230ef", "ef0231ef", "ef0232ef",
			"ef0233ef", "ef0234ef", "ef0235ef", "ef0236ef", "ef0237ef",
			"ef0238ef", "ef0239ef", "ef0240ef", "ef0241ef", "ef0242ef",
			"ef0243ef", "ef0244ef", "ef0245ef", "ef0246ef", "ef0247ef",
			"ef0248ef", "ef0249ef", "ef0250ef", "ef0251ef", "ef0252ef",
			"ef0253ef", "ef0254ef", "ef0255ef", "ef0256ef", "ef0257ef",
			"ef0258ef", "ef0259ef", "ef0260ef", "ef0261ef", "ef0262ef",
			"ef0263ef", "ef0264ef", "ef0265ef", "ef0266ef", "ef0267ef",
			"ef0268ef", "ef0269ef", "ef0270ef", "ef0271ef", "ef0272ef",
			"ef0273ef", "ef0274ef", "ef0275ef", "ef0276ef", "ef0277ef",
			"ef0278ef", "ef0279ef", "ef0280ef", "ef0281ef", "ef0282ef",
			"ef0283ef", "ef0284ef", "ef0285ef", "ef0286ef", "ef0287ef",
			"ef0288ef", "ef0289ef", "ef0290ef", "ef0291ef", "ef0292ef",
			"ef0293ef", "ef0294ef", "ef0295ef", "ef0296ef", "ef0297ef",
			"ef0298ef", "ef0299ef", "ef0300ef", "ef0301ef", "ef0302ef",
			"ef0303ef", "ef0304ef", "ef0305ef", "ef0306ef", "ef0307ef",
			"ef0308ef", "ef0309ef", "ef0310ef", "ef0311ef", "ef0312ef",
			"ef0313ef", "ef0314ef", "ef0315ef", "ef0316ef", "ef0317ef",
			"ef0318ef", "ef0319ef", "ef0320ef", "ef0321ef", "ef0322ef",
			"ef0323ef", "ef0324ef", "ef0325ef", "ef0326ef", "ef0327ef",
			"ef0328ef", "ef0329ef", "ef0330ef", "ef0331ef", "ef0332ef",
			"ef0333ef", "ef0334ef", "ef0335ef", "ef0336ef", "ef0337ef",
			"ef0338ef", "ef0339ef", "ef0340ef", "ef0341ef", "ef0342ef",
			"ef0343ef", "ef0344ef", "ef0345ef", "ef0346ef", "ef0347ef",
			"ef0348ef", "ef0349ef", "ef0350ef", "ef0351ef", "ef0352ef",
			"ef0353ef", "ef0354ef", "ef0355ef", "ef0356ef", "ef0357ef",
			"ef0358ef", "ef0359ef", "ef0360ef", "ef0361ef", "ef0362ef",
			"ef0363ef", "ef0364ef", "ef0365ef", "ef0366ef", "ef0367ef",
			"ef0368ef", "ef0369ef", "ef0370ef", "ef0371ef", "ef0372ef",
			"ef0373ef", "ef0374ef", "ef0375ef", "ef0376ef", "ef0377ef",
			"ef0378ef", "ef0379ef", "ef0380ef", "ef0381ef", "ef0382ef",
			"ef0383ef", "ef0384ef", "ef0385ef", "ef0386ef", "ef0387ef",
			"ef0388ef", "ef0389ef", "ef0390ef", "ef0391ef", "ef0392ef",
			"ef0393ef", "ef0394ef", "ef0395ef", "ef0396ef", "ef0397ef",
			"ef0398ef", "ef0399ef", "ef0400ef", "ef0401ef", "ef0402ef",
			"ef0403ef", "ef0404ef", "ef0405ef", "ef0406ef", "ef0407ef",
			"ef0408ef", "ef0409ef", "ef0410ef", "ef0411ef", "ef0412ef",
			"ef0413ef", "ef0414ef", "ef0415ef", "ef0416ef", "ef0417ef",
			"ef0418ef", "ef0419ef", "ef0420ef", "ef0421ef", "ef0422ef",
			"ef0423ef", "ef0424ef", "ef0425ef", "ef0426ef", "ef0427ef",
			"ef0428ef", "ef0429ef", "ef0430ef", "ef0431ef", "ef0432ef",
			"ef0433ef", "ef0434ef", "ef0435ef", "ef0436ef", "ef0437ef",
			"ef0438ef", "ef0439ef", "ef0440ef", "ef0441ef", "ef0442ef",
			"ef0443ef", "ef0444ef", "ef0445ef", "ef0446ef", "ef0447ef",
			"ef0448ef", "ef0449ef", "ef0450ef", "ef0451ef", "ef0452ef",
			"ef0453ef", "ef0454ef", "ef0455ef", "ef0456ef", "ef0457ef",
			"ef0458ef", "ef0459ef", "ef0460ef", "ef0461ef", "ef0462ef",
			"ef0463ef", "ef0464ef", "ef0465ef", "ef0466ef", "ef0467ef",
			"ef0468ef", "ef0469ef", "ef0470ef", "ef0471ef", "ef0472ef",
			"ef0473ef", "ef0474ef", "ef0475ef", "ef0476ef", "ef0477ef",
			"ef0478ef", "ef0479ef", "ef0480ef", "ef0481ef", "ef0482ef",
			"ef0483ef", "ef0484ef", "ef0485ef", "ef0486ef", "ef0487ef",
			"ef0488ef", "ef0489ef", "ef0490ef", "ef0491ef", "ef0492ef",
			"ef0493ef", "ef0494ef", "ef0495ef", "ef0496ef", "ef0497ef",
			"ef0498ef", "ef0499ef", "ef0500ef", "ef0501ef", "ef0502ef",
			"ef0503ef", "ef0504ef", "ef0505ef", "ef0506ef", "ef0507ef",
			"ef0508ef", "ef0509ef", "ef0510ef", "ef0511ef", "ef0512ef",
			"ef0513ef", "ef0514ef", "ef0515ef", "ef0516ef", "ef0517ef",
			"ef0518ef", "ef0519ef", "ef0520ef", "ef0521ef", "ef0522ef",
			"ef0523ef", "ef0524ef", "ef0525ef", "ef0526ef", "ef0527ef",
			"ef0528ef", "ef0529ef", "ef0530ef", "ef0531ef", "ef0532ef",
			"ef0533ef", "ef0534ef", "ef0535ef", "ef0536ef", "ef0537ef",
			"ef0538ef", "ef0539ef", "ef0540ef", "ef0541ef", "ef0542ef",
			"ef0543ef", "ef0544ef", "ef0545ef", "ef0546ef", "ef0547ef",
			"ef0548ef", "ef0549ef", "ef0550ef", "ef0551ef", "ef0552ef",
			"ef0553ef", "ef0554ef", "ef0555ef", "ef0556ef", "ef0557ef",
			"ef0558ef", "ef0559ef", "ef0560ef", "ef0561ef", "ef0562ef",
			"ef0563ef", "ef0564ef", "ef0565ef", "ef0566ef", "ef0567ef",
			"ef0568ef", "ef0569ef", "ef0570ef", "ef0571ef", "ef0572ef",
			"ef0573ef", "ef0574ef", "ef0575ef", "ef0576ef", "ef0577ef",
			"ef0578ef", "ef0579ef", "ef0580ef", "ef0581ef", "ef0582ef",
			"ef0583ef", "ef0584ef", "ef0585ef", "ef0586ef", "ef0587ef",
			"ef0588ef", "ef0589ef", "ef0590ef", "ef0591ef", "ef0592ef",
			"ef0593ef", "ef0594ef", "ef0595ef", "ef0596ef", "ef0597ef",
			"ef0598ef", "ef0599ef", "ef0600ef", "ef0601ef", "ef0602ef",
			"ef0603ef", "ef0604ef", "ef0605ef", "ef0606ef", "ef0607ef",
			"ef0608ef", "ef0609ef", "ef0610ef", "ef0611ef", "ef0612ef",
			"ef0613ef", "ef0614ef", "ef0615ef", "ef0616ef", "ef0617ef",
			"ef0618ef", "ef0619ef", "ef0620ef", "ef0621ef", "ef0622ef",
			"ef0623ef", "ef0624ef", "ef0625ef", "ef0626ef", "ef0627ef",
			"ef0628ef", "ef0629ef", "ef0630ef", "ef0631ef", "ef0632ef",
			"ef0633ef", "ef0634ef", "ef0635ef", "ef0636ef", "ef0637ef",
			"ef0638ef", "ef0639ef", "ef0640ef", "ef0641ef", "ef0642ef",
			"ef0643ef", "ef0644ef", "ef0645ef", "ef0646ef", "ef0647ef",
			"ef0648ef", "ef0649ef", "ef0650ef", "ef0651ef", "ef0652ef",
			"ef0653ef", "ef0654ef", "ef0655ef", "ef0656ef", "ef0657ef",
			"ef0658ef", "ef0659ef", "ef0660ef", "ef0661ef", "ef0662ef",
			"ef0663ef", "ef0664ef", "ef0665ef", "ef0666ef", "ef0667ef",
			"ef0668ef", "ef0669ef", "ef0670ef", "ef0671ef", "ef0672ef",
			"ef0673ef", "ef0674ef", "ef0675ef", "ef0676ef", "ef0677ef",
			"ef0678ef", "ef0679ef", "ef0680ef", "ef0681ef", "ef0682ef",
			"ef0683ef", "ef0684ef", "ef0685ef", "ef0686ef", "ef0687ef",
			"ef0688ef", "ef0689ef", "ef0690ef", "ef0691ef", "ef0692ef",
			"ef0693ef", "ef0694ef", "ef0695ef", "ef0696ef", "ef0697ef",
			"ef0698ef", "ef0699ef", "ef0700ef", "ef0701ef", "ef0702ef",
			"ef0703ef", "ef0704ef", "ef0705ef", "ef0706ef", "ef0707ef",
			"ef0708ef", "ef0709ef", "ef0710ef", "ef0711ef", "ef0712ef",
			"ef0713ef", "ef0714ef", "ef0715ef", "ef0716ef", "ef0717ef",
			"ef0718ef", "ef0719ef", "ef0720ef", "ef0721ef", "ef0722ef",
			"ef0723ef", "ef0724ef", "ef0725ef", "ef0726ef", "ef0727ef",
			"ef0728ef", "ef0729ef", "ef0730ef", "ef0731ef", "ef0732ef",
			"ef0733ef", "ef0734ef", "ef0735ef", "ef0736ef", "ef0737ef",
			"ef0738ef", "ef0739ef", "ef0740ef", "ef0741ef", "ef0742ef",
			"ef0743ef", "ef0744ef", "ef0745ef", "ef0746ef", "ef0747ef",
			"ef0748ef", "ef0749ef", "ef0750ef", "ef0751ef", "ef0752ef",
			"ef0753ef", "ef0754ef", "ef0755ef", "ef0756ef", "ef0757ef",
			"ef0758ef", "ef0759ef", "ef0760ef", "ef0761ef", "ef0762ef",
			"ef0763ef", "ef0764ef", "ef0765ef", "ef0766ef", "ef0767ef",
			"ef0768ef", "ef0769ef", "ef0770ef", "ef0771ef", "ef0772ef",
			"ef0773ef", "ef0774ef", "ef0775ef", "ef0776ef", "ef0777ef",
			"ef0778ef", "ef0779ef", "ef0780ef", "ef0781ef", "ef0782ef",
			"ef0783ef", "ef0784ef", "ef0785ef", "ef0786ef", "ef0787ef",
			"ef0788ef", "ef0789ef", "ef0790ef", "ef0791ef", "ef0792ef",
			"ef0793ef", "ef0794ef", "ef0795ef", "ef0796ef", "ef0797ef",
			"ef0798ef", "ef0799ef", "ef0800ef", "ef0801ef", "ef0802ef",
			"ef0803ef", "ef0804ef", "ef0805ef", "ef0806ef", "ef0807ef",
			"ef0808ef", "ef0809ef", "ef0810ef", "ef0811ef", "ef0812ef",
			"ef0813ef", "ef0814ef", "ef0815ef", "ef0816ef", "ef0817ef",
			"ef0818ef", "ef0819ef", "ef0820ef", "ef0821ef", "ef0822ef",
			"ef0823ef", "ef0824ef", "ef0825ef", "ef0826ef", "ef0827ef",
			"ef0828ef", "ef0829ef", "ef0830ef", "ef0831ef", "ef0832ef",
			"ef0833ef", "ef0834ef", "ef0835ef", "ef0836ef", "ef0837ef",
			"ef0838ef", "ef0839ef", "ef0840ef", "ef0841ef", "ef0842ef",
			"ef0843ef", "ef0844ef", "ef0845ef", "ef0846ef", "ef0847ef",
			"ef0848ef", "ef0849ef", "ef0850ef", "ef0851ef", "ef0852ef",
			"ef0853ef", "ef0854ef", "ef0855ef", "ef0856ef", "ef0857ef",
			"ef0858ef", "ef0859ef", "ef0860ef", "ef0861ef", "ef0862ef",
			"ef0863ef", "ef0864ef", "ef0865ef", "ef0866ef", "ef0867ef",
			"ef0868ef", "ef0869ef", "ef0870ef", "ef0871ef", "ef0872ef",
			"ef0873ef", "ef0874ef", "ef0875ef", "ef0876ef", "ef0877ef",
			"ef0878ef", "ef0879ef", "ef0880ef", "ef0881ef", "ef0882ef",
			"ef0883ef", "ef0884ef", "ef0885ef", "ef0886ef", "ef0887ef",
			"ef0888ef", "ef0889ef", "ef0890ef", "ef0891ef", "ef0892ef",
			"ef0893ef", "ef0894ef", "ef0895ef", "ef0896ef", "ef0897ef",
			"ef0898ef", "ef0899ef", "ef0900ef", "ef0901ef", "ef0902ef",
			"ef0903ef", "ef0904ef", "ef0905ef", "ef0906ef", "ef0907ef",
			"ef0908ef", "ef0909ef", "ef0910ef", "ef0911ef", "ef0912ef",
			"ef0913ef", "ef0914ef", "ef0915ef", "ef0916ef", "ef0917ef",
			"ef0918ef", "ef0919ef", "ef0920ef", "ef0921ef", "ef0922ef",
			"ef0923ef", "ef0924ef", "ef0925ef", "ef0926ef", "ef0927ef",
			"ef0928ef", "ef0929ef", "ef0930ef", "ef0931ef", "ef0932ef",
			"ef0933ef", "ef0934ef", "ef0935ef", "ef0936ef", "ef0937ef",
			"ef0938ef", "ef0939ef", "ef0940ef", "ef0941ef", "ef0942ef",
			"ef0943ef", "ef0944ef", "ef0945ef", "ef0946ef", "ef0947ef",
			"ef0948ef", "ef0949ef", "ef0950ef", "ef0951ef", "ef0952ef",
			"ef0953ef", "ef0954ef", "ef0955ef", "ef0956ef", "ef0957ef",
			"ef0958ef", "ef0959ef", "ef0960ef", "ef0961ef", "ef0962ef",
			"ef0963ef", "ef0964ef", "ef0965ef", "ef0966ef", "ef0967ef",
			"ef0968ef", "ef0969ef", "ef0970ef", "ef0971ef", "ef0972ef",
			"ef0973ef", "ef0974ef", "ef0975ef", "ef0976ef", "ef0977ef",
			"ef0978ef", "ef0979ef", "ef0980ef", "ef0981ef", "ef0982ef",
			"ef0983ef", "ef0984ef", "ef0985ef", "ef0986ef", "ef0987ef",
			"ef0988ef", "ef0989ef", "ef0989ef" };
	private static String[] file_name1 = { "1", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "10", "11", "12" };

	/**
	 * 
	 * @description 替换为Unicode中对应的字符
	 * @update Oct 12, 2014 8:35:10 PM
	 */
	public static String convertTag3(String str, Context activity) {
		str = str.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>");
		Pattern p = Pattern.compile("&lt;ef[0-9]{4}ef&gt;");
		Matcher m = p.matcher(str);
		while (m.find()) {
			int index = Integer.parseInt(m.group().replace("&lt;ef", "")
					.replace("ef&gt;", ""));
			Logcat.v("index" + index);
			if (index < file_name.length) {
				// str = str.replaceAll(m.group(), "<img src=\""+
				// file_name[index]+"\"/>");
				str = str.replaceAll(m.group(), parserPlist(activity, index));
			}
		}
		return str;
	}
	
	/**
	 * 
	 * @description 搜狗输入法等输入emoji字符
	 * @update Oct 12, 2014 8:54:40 PM
	 */
	public static String convertTag4(String str, Context activity) {
		PListXMLParser parser = new PListXMLParser(); // 基于SAX的实现
		PListXMLHandler handler = new PListXMLHandler();
		parser.setHandler(handler);

		try {
			parser.parse(activity.getAssets().open(EMOJI_PLIST)); // area.plist是你要解析的文件，该文件需放在assets文件夹下
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		PList actualPList = ((PListXMLHandler) parser.getHandler()).getPlist();
		Array districts = (Array) actualPList.getRootElement();

		for (int k = 0; k < districts.size(); k++) {
			com.longevitysoft.android.xml.plist.domain.String district;
			district = (com.longevitysoft.android.xml.plist.domain.String) districts.get(k);
			String value = district.getValue();
			StringBuffer sb = new StringBuffer("");
			sb.append("&lt;ef");
			for (int j = 0; j < (4 - ("" + k).length()); j++) {
				sb.append('0');
			}
			sb.append(k);
			sb.append("ef&gt;");
			String strEf = sb.toString();
			str = str.replaceAll(value, strEf);
			
			Logcat.d(TAG, value + ":" + strEf);
		}
		str = str.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
		return str;
	}

	public static String parserPlist(final Context activity, int index) {
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		PListXMLParser parser = new PListXMLParser(); // 基于SAX的实现
		PListXMLHandler handler = new PListXMLHandler();
		parser.setHandler(handler);

		try {
			parser.parse(activity.getAssets().open(EMOJI_PLIST)); // area.plist是你要解析的文件，该文件需放在assets文件夹下
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		PList actualPList = ((PListXMLHandler) parser.getHandler()).getPlist();
		Array districts = (Array) actualPList.getRootElement();

		// for(int k=0;k<districts.size();k++) {
		if (index < districts.size()) {
			com.longevitysoft.android.xml.plist.domain.String district = (com.longevitysoft.android.xml.plist.domain.String) districts
					.get(index);
			
			Logcat.v(TAG, "表情为:" + district.getValue());
			return district.getValue();
		}
		return "";
		// }
		// }
		//
		// }).start();
	}
	
	public static String convertTag2(String str) {
		str = str.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>");
		Pattern p = Pattern.compile("&lt;ef[0-9]{1}ef&gt;");
		Matcher m = p.matcher(str);
		while (m.find()) {
			int index = Integer.parseInt(m.group().replace("&lt;ef", "")
					.replace("ef&gt;", ""));
			Logcat.v("index" + index);
			if (index < file_name.length) {
				str = str.replaceAll(m.group(), "<img src=\""
						+ file_name1[index] + "\"/>");
			}
		}
		return str;
	}

	public static String convertTag(String str) {
		str = str.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>");
		Pattern p = Pattern.compile("&lt;ef[0-9]{4}ef&gt;");
		Matcher m = p.matcher(str);
		while (m.find()) {
			int index = Integer.parseInt(m.group().replace("&lt;ef", "")
					.replace("ef&gt;", ""));
			Logcat.v("index" + index);
			if (index < file_name.length) {
				str = str.replaceAll(m.group(), "<img src=\""
						+ file_name[index] + "\"/>");
			}
		}
		return str;

		// return str.replaceAll("<", "&lt;").replaceAll(">", "&gt;")
		// .replaceAll("&lt;ef0000ef&gt;", "<img src=\"e415\"/>")
		// .replaceAll("&lt;ef0001ef&gt;", "<img src=\"e056\"/>")
		// .replaceAll("&lt;ef0002ef&gt;", "<img src=\"e057\"/>")
		// .replaceAll("&lt;ef0003ef&gt;", "<img src=\"e414\"/>")
		// .replaceAll("&lt;ef0004ef&gt;", "<img src=\"e405\"/>")
		// .replaceAll("&lt;ef0005ef&gt;", "<img src=\"e106\"/>")
		// .replaceAll("&lt;ef0006ef&gt;", "<img src=\"e418\"/>")
		// .replaceAll("&lt;ef0007ef&gt;", "<img src=\"e417\"/>")
		// .replaceAll("&lt;ef0008ef&gt;", "<img src=\"e40d\"/>")
		// .replaceAll("&lt;ef0009ef&gt;", "<img src=\"e40a\"/>")
		// .replaceAll("&lt;ef0010ef&gt;", "<img src=\"e404\"/>")
		// .replaceAll("&lt;ef0011ef&gt;", "<img src=\"e105\"/>")
		// .replaceAll("&lt;ef0012ef&gt;", "<img src=\"e409\"/>")
		// .replaceAll("&lt;ef0013ef&gt;", "<img src=\"e40e\"/>")
		// .replaceAll("&lt;ef0014ef&gt;", "<img src=\"e402\"/>")
		// .replaceAll("&lt;ef0015ef&gt;", "<img src=\"e108\"/>")
		// .replaceAll("&lt;ef0016ef&gt;", "<img src=\"e403\"/>")
		// .replaceAll("&lt;ef0017ef&gt;", "<img src=\"e058\"/>")
		// .replaceAll("&lt;ef0018ef&gt;", "<img src=\"e407\"/>")
		// .replaceAll("&lt;ef0019ef&gt;", "<img src=\"e401\"/>")
		// .replaceAll("&lt;ef0020ef&gt;", "<img src=\"e40f\"/>")
		// .replaceAll("&lt;ef0021ef&gt;", "<img src=\"e40b\"/>")
		// .replaceAll("&lt;ef0022ef&gt;", "<img src=\"e406\"/>")
		// .replaceAll("&lt;ef0023ef&gt;", "<img src=\"e413\"/>")
		// .replaceAll("&lt;ef0024ef&gt;", "<img src=\"e411\"/>")
		// .replaceAll("&lt;ef0025ef&gt;", "<img src=\"e412\"/>")
		// .replaceAll("&lt;ef0026ef&gt;", "<img src=\"e410\"/>")
		// .replaceAll("&lt;ef0027ef&gt;", "<img src=\"e107\"/>")
		// .replaceAll("&lt;ef0028ef&gt;", "<img src=\"e059\"/>")
		// .replaceAll("&lt;ef0029ef&gt;", "<img src=\"e416\"/>")
		// .replaceAll("&lt;ef0030ef&gt;", "<img src=\"e408\"/>")
		// .replaceAll("&lt;ef0031ef&gt;", "<img src=\"e40c\"/>")
		// .replaceAll("&lt;ef0032ef&gt;", "<img src=\"e11a\"/>")
		// .replaceAll("&lt;ef0033ef&gt;", "<img src=\"e10c\"/>")
		// .replaceAll("&lt;ef0034ef&gt;", "<img src=\"e32c\"/>")
		// .replaceAll("&lt;ef0035ef&gt;", "<img src=\"e32a\"/>")
		// .replaceAll("&lt;ef0036ef&gt;", "<img src=\"e32d\"/>")
		// .replaceAll("&lt;ef0037ef&gt;", "<img src=\"e328\"/>")
		// .replaceAll("&lt;ef0038ef&gt;", "<img src=\"e32b\"/>")
		// .replaceAll("&lt;ef0039ef&gt;", "<img src=\"e022\"/>")
		// .replaceAll("&lt;ef0040ef&gt;", "<img src=\"e023\"/>")
		// .replaceAll("&lt;ef0041ef&gt;", "<img src=\"e327\"/>")
		// .replaceAll("&lt;ef0042ef&gt;", "<img src=\"e329\"/>")
		// .replaceAll("&lt;ef0043ef&gt;", "<img src=\"e32e\"/>")
		// .replaceAll("&lt;ef0044ef&gt;", "<img src=\"e335\"/>")
		// .replaceAll("&lt;ef0045ef&gt;", "<img src=\"e334\"/>")
		// .replaceAll("&lt;ef0046ef&gt;", "<img src=\"e337\"/>")
		// .replaceAll("&lt;ef0047ef&gt;", "<img src=\"e336\"/>")
		// .replaceAll("&lt;ef0048ef&gt;", "<img src=\"e13c\"/>")
		// .replaceAll("&lt;ef0049ef&gt;", "<img src=\"e330\"/>")
		// .replaceAll("&lt;ef0050ef&gt;", "<img src=\"e331\"/>")
		// .replaceAll("&lt;ef0051ef&gt;", "<img src=\"e326\"/>")
		// .replaceAll("&lt;ef0052ef&gt;", "<img src=\"e03e\"/>")
		// .replaceAll("&lt;ef0053ef&gt;", "<img src=\"e11d\"/>")
		// .replaceAll("&lt;ef0054ef&gt;", "<img src=\"e05a\"/>")
		// .replaceAll("&lt;ef0055ef&gt;", "<img src=\"e00e\"/>")
		// .replaceAll("&lt;ef0056ef&gt;", "<img src=\"e421\"/>")
		// .replaceAll("&lt;ef0057ef&gt;", "<img src=\"e420\"/>")
		// .replaceAll("&lt;ef0058ef&gt;", "<img src=\"e00d\"/>")
		// .replaceAll("&lt;ef0059ef&gt;", "<img src=\"e010\"/>")
		// .replaceAll("&lt;ef0060ef&gt;", "<img src=\"e011\"/>")
		// .replaceAll("&lt;ef0061ef&gt;", "<img src=\"e41e\"/>")
		// .replaceAll("&lt;ef0062ef&gt;", "<img src=\"e012\"/>")
		// .replaceAll("&lt;ef0063ef&gt;", "<img src=\"e422\"/>")
		// .replaceAll("&lt;ef0064ef&gt;", "<img src=\"e22e\"/>")
		// .replaceAll("&lt;ef0065ef&gt;", "<img src=\"e22f\"/>")
		// .replaceAll("&lt;ef0066ef&gt;", "<img src=\"e231\"/>")
		// .replaceAll("&lt;ef0067ef&gt;", "<img src=\"e230\"/>")
		// .replaceAll("&lt;ef0068ef&gt;", "<img src=\"e427\"/>")
		// .replaceAll("&lt;ef0069ef&gt;", "<img src=\"e41d\"/>")
		// .replaceAll("&lt;ef0070ef&gt;", "<img src=\"e00f\"/>")
		// .replaceAll("&lt;ef0071ef&gt;", "<img src=\"e41f\"/>")
		// .replaceAll("&lt;ef0072ef&gt;", "<img src=\"e14c\"/>")
		// .replaceAll("&lt;ef0073ef&gt;", "<img src=\"e201\"/>")
		// .replaceAll("&lt;ef0074ef&gt;", "<img src=\"e115\"/>")
		// .replaceAll("&lt;ef0075ef&gt;", "<img src=\"e428\"/>")
		// .replaceAll("&lt;ef0076ef&gt;", "<img src=\"e51f\"/>")
		// .replaceAll("&lt;ef0077ef&gt;", "<img src=\"e429\"/>")
		// .replaceAll("&lt;ef0078ef&gt;", "<img src=\"e424\"/>")
		// .replaceAll("&lt;ef0079ef&gt;", "<img src=\"e423\"/>")
		// .replaceAll("&lt;ef0080ef&gt;", "<img src=\"e253\"/>")
		// .replaceAll("&lt;ef0081ef&gt;", "<img src=\"e426\"/>")
		// .replaceAll("&lt;ef0082ef&gt;", "<img src=\"e111\"/>")
		// .replaceAll("&lt;ef0083ef&gt;", "<img src=\"e425\"/>")
		// .replaceAll("&lt;ef0084ef&gt;", "<img src=\"e31e\"/>")
		// .replaceAll("&lt;ef0085ef&gt;", "<img src=\"e31f\"/>")
		// .replaceAll("&lt;ef0086ef&gt;", "<img src=\"e31d\"/>")
		// .replaceAll("&lt;ef0087ef&gt;", "<img src=\"e001\"/>")
		// .replaceAll("&lt;ef0088ef&gt;", "<img src=\"e002\"/>")
		// .replaceAll("&lt;ef0089ef&gt;", "<img src=\"e005\"/>")
		// .replaceAll("&lt;ef0090ef&gt;", "<img src=\"e004\"/>")
		// .replaceAll("&lt;ef0091ef&gt;", "<img src=\"e51a\"/>")
		// .replaceAll("&lt;ef0092ef&gt;", "<img src=\"e519\"/>")
		// .replaceAll("&lt;ef0093ef&gt;", "<img src=\"e518\"/>")
		// .replaceAll("&lt;ef0094ef&gt;", "<img src=\"e515\"/>")
		// .replaceAll("&lt;ef0095ef&gt;", "<img src=\"e516\"/>")
		// .replaceAll("&lt;ef0096ef&gt;", "<img src=\"e517\"/>")
		// .replaceAll("&lt;ef0097ef&gt;", "<img src=\"e51b\"/>")
		// .replaceAll("&lt;ef0098ef&gt;", "<img src=\"e152\"/>")
		// .replaceAll("&lt;ef0099ef&gt;", "<img src=\"e04e\"/>")
		// .replaceAll("&lt;ef0100ef&gt;", "<img src=\"e51c\"/>")
		// .replaceAll("&lt;ef0101ef&gt;", "<img src=\"e51e\"/>")
		// .replaceAll("&lt;ef0102ef&gt;", "<img src=\"e11c\"/>")
		// .replaceAll("&lt;ef0103ef&gt;", "<img src=\"e536\"/>")
		// .replaceAll("&lt;ef0104ef&gt;", "<img src=\"e003\"/>")
		// .replaceAll("&lt;ef0105ef&gt;", "<img src=\"e41c\"/>")
		// .replaceAll("&lt;ef0106ef&gt;", "<img src=\"e41b\"/>")
		// .replaceAll("&lt;ef0107ef&gt;", "<img src=\"e419\"/>")
		// .replaceAll("&lt;ef0108ef&gt;", "<img src=\"e41a\"/>")
		// .replaceAll("&lt;ef0109ef&gt;", "<img src=\"e436\"/>")
		// .replaceAll("&lt;ef0110ef&gt;", "<img src=\"e437\"/>")
		// .replaceAll("&lt;ef0111ef&gt;", "<img src=\"e438\"/>")
		// .replaceAll("&lt;ef0112ef&gt;", "<img src=\"e43a\"/>")
		// .replaceAll("&lt;ef0113ef&gt;", "<img src=\"e439\"/>")
		// .replaceAll("&lt;ef0114ef&gt;", "<img src=\"e43b\"/>")
		// .replaceAll("&lt;ef0115ef&gt;", "<img src=\"e117\"/>")
		// .replaceAll("&lt;ef0116ef&gt;", "<img src=\"e440\"/>")
		// .replaceAll("&lt;ef0117ef&gt;", "<img src=\"e442\"/>")
		// .replaceAll("&lt;ef0118ef&gt;", "<img src=\"e446\"/>")
		// .replaceAll("&lt;ef0119ef&gt;", "<img src=\"e445\"/>")
		// .replaceAll("&lt;ef0120ef&gt;", "<img src=\"e11b\"/>")
		// .replaceAll("&lt;ef0121ef&gt;", "<img src=\"e448\"/>")
		// .replaceAll("&lt;ef0122ef&gt;", "<img src=\"e033\"/>")
		// .replaceAll("&lt;ef0123ef&gt;", "<img src=\"e112\"/>")
		// .replaceAll("&lt;ef0124ef&gt;", "<img src=\"e325\"/>")
		// .replaceAll("&lt;ef0125ef&gt;", "<img src=\"e312\"/>")
		// .replaceAll("&lt;ef0126ef&gt;", "<img src=\"e310\"/>")
		// .replaceAll("&lt;ef0127ef&gt;", "<img src=\"e126\"/>")
		// .replaceAll("&lt;ef0128ef&gt;", "<img src=\"e127\"/>")
		// .replaceAll("&lt;ef0129ef&gt;", "<img src=\"e008\"/>")
		// .replaceAll("&lt;ef0130ef&gt;", "<img src=\"e03d\"/>")
		// .replaceAll("&lt;ef0131ef&gt;", "<img src=\"e00c\"/>")
		// .replaceAll("&lt;ef0132ef&gt;", "<img src=\"e12a\"/>")
		// .replaceAll("&lt;ef0133ef&gt;", "<img src=\"e00a\"/>")
		// .replaceAll("&lt;ef0134ef&gt;", "<img src=\"e00b\"/>")
		// .replaceAll("&lt;ef0135ef&gt;", "<img src=\"e009\"/>")
		// .replaceAll("&lt;ef0136ef&gt;", "<img src=\"e316\"/>")
		// .replaceAll("&lt;ef0137ef&gt;", "<img src=\"e129\"/>")
		// .replaceAll("&lt;ef0138ef&gt;", "<img src=\"e141\"/>")
		// .replaceAll("&lt;ef0139ef&gt;", "<img src=\"e142\"/>")
		// .replaceAll("&lt;ef0140ef&gt;", "<img src=\"e317\"/>")
		// .replaceAll("&lt;ef0141ef&gt;", "<img src=\"e128\"/>")
		// .replaceAll("&lt;ef0142ef&gt;", "<img src=\"e14b\"/>")
		// .replaceAll("&lt;ef0143ef&gt;", "<img src=\"e211\"/>")
		// .replaceAll("&lt;ef0144ef&gt;", "<img src=\"e114\"/>")
		// .replaceAll("&lt;ef0145ef&gt;", "<img src=\"e145\"/>")
		// .replaceAll("&lt;ef0146ef&gt;", "<img src=\"e144\"/>")
		// .replaceAll("&lt;ef0147ef&gt;", "<img src=\"e03f\"/>")
		// .replaceAll("&lt;ef0148ef&gt;", "<img src=\"e313\"/>")
		// .replaceAll("&lt;ef0149ef&gt;", "<img src=\"e116\"/>")
		// .replaceAll("&lt;ef0150ef&gt;", "<img src=\"e10f\"/>")
		// .replaceAll("&lt;ef0151ef&gt;", "<img src=\"e104\"/>")
		// .replaceAll("&lt;ef0152ef&gt;", "<img src=\"e103\"/>")
		// .replaceAll("&lt;ef0153ef&gt;", "<img src=\"e101\"/>")
		// .replaceAll("&lt;ef0154ef&gt;", "<img src=\"e102\"/>")
		// .replaceAll("&lt;ef0155ef&gt;", "<img src=\"e13f\"/>")
		// .replaceAll("&lt;ef0156ef&gt;", "<img src=\"e140\"/>")
		// .replaceAll("&lt;ef0157ef&gt;", "<img src=\"e11f\"/>")
		// .replaceAll("&lt;ef0158ef&gt;", "<img src=\"e12f\"/>")
		// .replaceAll("&lt;ef0159ef&gt;", "<img src=\"e031\"/>")
		// .replaceAll("&lt;ef0160ef&gt;", "<img src=\"e30e\"/>")
		// .replaceAll("&lt;ef0161ef&gt;", "<img src=\"e311\"/>")
		// .replaceAll("&lt;ef0162ef&gt;", "<img src=\"e113\"/>")
		// .replaceAll("&lt;ef0163ef&gt;", "<img src=\"e30f\"/>")
		// .replaceAll("&lt;ef0164ef&gt;", "<img src=\"e13b\"/>")
		// .replaceAll("&lt;ef0165ef&gt;", "<img src=\"e42b\"/>")
		// .replaceAll("&lt;ef0166ef&gt;", "<img src=\"e42a\"/>")
		// .replaceAll("&lt;ef0167ef&gt;", "<img src=\"e018\"/>")
		// .replaceAll("&lt;ef0168ef&gt;", "<img src=\"e016\"/>")
		// .replaceAll("&lt;ef0169ef&gt;", "<img src=\"e015\"/>")
		// .replaceAll("&lt;ef0170ef&gt;", "<img src=\"e014\"/>")
		// .replaceAll("&lt;ef0171ef&gt;", "<img src=\"e42c\"/>")
		// .replaceAll("&lt;ef0172ef&gt;", "<img src=\"e42d\"/>")
		// .replaceAll("&lt;ef0173ef&gt;", "<img src=\"e017\"/>")
		// .replaceAll("&lt;ef0174ef&gt;", "<img src=\"e013\"/>")
		// .replaceAll("&lt;ef0175ef&gt;", "<img src=\"e20e\"/>")
		// .replaceAll("&lt;ef0176ef&gt;", "<img src=\"e20c\"/>")
		// .replaceAll("&lt;ef0177ef&gt;", "<img src=\"e20f\"/>")
		// .replaceAll("&lt;ef0178ef&gt;", "<img src=\"e20d\"/>")
		// .replaceAll("&lt;ef0179ef&gt;", "<img src=\"e131\"/>")
		// .replaceAll("&lt;ef0180ef&gt;", "<img src=\"e12b\"/>")
		// .replaceAll("&lt;ef0181ef&gt;", "<img src=\"e130\"/>")
		// .replaceAll("&lt;ef0182ef&gt;", "<img src=\"e12d\"/>")
		// .replaceAll("&lt;ef0183ef&gt;", "<img src=\"e324\"/>")
		// .replaceAll("&lt;ef0184ef&gt;", "<img src=\"e301\"/>")
		// .replaceAll("&lt;ef0185ef&gt;", "<img src=\"e148\"/>")
		// .replaceAll("&lt;ef0186ef&gt;", "<img src=\"e502\"/>")
		// .replaceAll("&lt;ef0187ef&gt;", "<img src=\"e03c\"/>")
		// .replaceAll("&lt;ef0188ef&gt;", "<img src=\"e30a\"/>")
		// .replaceAll("&lt;ef0189ef&gt;", "<img src=\"e042\"/>")
		// .replaceAll("&lt;ef0190ef&gt;", "<img src=\"e040\"/>")
		// .replaceAll("&lt;ef0191ef&gt;", "<img src=\"e041\"/>")
		// .replaceAll("&lt;ef0192ef&gt;", "<img src=\"e12c\"/>")
		// .replaceAll("&lt;ef0193ef&gt;", "<img src=\"e007\"/>")
		// .replaceAll("&lt;ef0194ef&gt;", "<img src=\"e31a\"/>")
		// .replaceAll("&lt;ef0195ef&gt;", "<img src=\"e13e\"/>")
		// .replaceAll("&lt;ef0196ef&gt;", "<img src=\"e31b\"/>")
		// .replaceAll("&lt;ef0197ef&gt;", "<img src=\"e006\"/>")
		// .replaceAll("&lt;ef0198ef&gt;", "<img src=\"e302\"/>")
		// .replaceAll("&lt;ef0199ef&gt;", "<img src=\"e319\"/>")
		// .replaceAll("&lt;ef0200ef&gt;", "<img src=\"e321\"/>")
		// .replaceAll("&lt;ef0201ef&gt;", "<img src=\"e322\"/>")
		// .replaceAll("&lt;ef0202ef&gt;", "<img src=\"e314\"/>")
		// .replaceAll("&lt;ef0203ef&gt;", "<img src=\"e503\"/>")
		// .replaceAll("&lt;ef0204ef&gt;", "<img src=\"e10e\"/>")
		// .replaceAll("&lt;ef0205ef&gt;", "<img src=\"e318\"/>")
		// .replaceAll("&lt;ef0206ef&gt;", "<img src=\"e43c\"/>")
		// .replaceAll("&lt;ef0207ef&gt;", "<img src=\"e11e\"/>")
		// .replaceAll("&lt;ef0208ef&gt;", "<img src=\"e323\"/>")
		// .replaceAll("&lt;ef0209ef&gt;", "<img src=\"e31c\"/>")
		// .replaceAll("&lt;ef0210ef&gt;", "<img src=\"e034\"/>")
		// .replaceAll("&lt;ef0211ef&gt;", "<img src=\"e035\"/>")
		// .replaceAll("&lt;ef0212ef&gt;", "<img src=\"e045\"/>")
		// .replaceAll("&lt;ef0213ef&gt;", "<img src=\"e338\"/>")
		// .replaceAll("&lt;ef0214ef&gt;", "<img src=\"e047\"/>")
		// .replaceAll("&lt;ef0215ef&gt;", "<img src=\"e30c\"/>")
		// .replaceAll("&lt;ef0216ef&gt;", "<img src=\"e044\"/>")
		// .replaceAll("&lt;ef0217ef&gt;", "<img src=\"e30b\"/>")
		// .replaceAll("&lt;ef0218ef&gt;", "<img src=\"e043\"/>")
		// .replaceAll("&lt;ef0219ef&gt;", "<img src=\"e120\"/>")
		// .replaceAll("&lt;ef0220ef&gt;", "<img src=\"e33b\"/>")
		// .replaceAll("&lt;ef0221ef&gt;", "<img src=\"e33f\"/>")
		// .replaceAll("&lt;ef0222ef&gt;", "<img src=\"e341\"/>")
		// .replaceAll("&lt;ef0223ef&gt;", "<img src=\"e34c\"/>")
		// .replaceAll("&lt;ef0224ef&gt;", "<img src=\"e344\"/>")
		// .replaceAll("&lt;ef0225ef&gt;", "<img src=\"e342\"/>")
		// .replaceAll("&lt;ef0226ef&gt;", "<img src=\"e33d\"/>")
		// .replaceAll("&lt;ef0227ef&gt;", "<img src=\"e33e\"/>")
		// .replaceAll("&lt;ef0228ef&gt;", "<img src=\"e340\"/>")
		// .replaceAll("&lt;ef0229ef&gt;", "<img src=\"e34d\"/>")
		// .replaceAll("&lt;ef0230ef&gt;", "<img src=\"e339\"/>")
		// .replaceAll("&lt;ef0231ef&gt;", "<img src=\"e147\"/>")
		// .replaceAll("&lt;ef0232ef&gt;", "<img src=\"e343\"/>")
		// .replaceAll("&lt;ef0233ef&gt;", "<img src=\"e33c\"/>")
		// .replaceAll("&lt;ef0234ef&gt;", "<img src=\"e33a\"/>")
		// .replaceAll("&lt;ef0235ef&gt;", "<img src=\"e43f\"/>")
		// .replaceAll("&lt;ef0236ef&gt;", "<img src=\"e34b\"/>")
		// .replaceAll("&lt;ef0237ef&gt;", "<img src=\"e046\"/>")
		// .replaceAll("&lt;ef0238ef&gt;", "<img src=\"e345\"/>")
		// .replaceAll("&lt;ef0239ef&gt;", "<img src=\"e346\"/>")
		// .replaceAll("&lt;ef0240ef&gt;", "<img src=\"e348\"/>")
		// .replaceAll("&lt;ef0241ef&gt;", "<img src=\"e347\"/>")
		// .replaceAll("&lt;ef0242ef&gt;", "<img src=\"e34a\"/>")
		// .replaceAll("&lt;ef0243ef&gt;", "<img src=\"e349\"/>")
		// .replaceAll("&lt;ef0244ef&gt;", "<img src=\"e04a\"/>")
		// .replaceAll("&lt;ef0245ef&gt;", "<img src=\"e04b\"/>")
		// .replaceAll("&lt;ef0246ef&gt;", "<img src=\"e049\"/>")
		// .replaceAll("&lt;ef0247ef&gt;", "<img src=\"e048\"/>")
		// .replaceAll("&lt;ef0248ef&gt;", "<img src=\"e04c\"/>")
		// .replaceAll("&lt;ef0249ef&gt;", "<img src=\"e13d\"/>")
		// .replaceAll("&lt;ef0250ef&gt;", "<img src=\"e443\"/>")
		// .replaceAll("&lt;ef0251ef&gt;", "<img src=\"e43e\"/>")
		// .replaceAll("&lt;ef0252ef&gt;", "<img src=\"e04f\"/>")
		// .replaceAll("&lt;ef0253ef&gt;", "<img src=\"e052\"/>")
		// .replaceAll("&lt;ef0254ef&gt;", "<img src=\"e053\"/>")
		// .replaceAll("&lt;ef0255ef&gt;", "<img src=\"e524\"/>")
		// .replaceAll("&lt;ef0256ef&gt;", "<img src=\"e52c\"/>")
		// .replaceAll("&lt;ef0257ef&gt;", "<img src=\"e52a\"/>")
		// .replaceAll("&lt;ef0258ef&gt;", "<img src=\"e531\"/>")
		// .replaceAll("&lt;ef0259ef&gt;", "<img src=\"e050\"/>")
		// .replaceAll("&lt;ef0260ef&gt;", "<img src=\"e527\"/>")
		// .replaceAll("&lt;ef0261ef&gt;", "<img src=\"e051\"/>")
		// .replaceAll("&lt;ef0262ef&gt;", "<img src=\"e10b\"/>")
		// .replaceAll("&lt;ef0263ef&gt;", "<img src=\"e52b\"/>")
		// .replaceAll("&lt;ef0264ef&gt;", "<img src=\"e52f\"/>")
		// .replaceAll("&lt;ef0265ef&gt;", "<img src=\"e109\"/>")
		// .replaceAll("&lt;ef0266ef&gt;", "<img src=\"e528\"/>")
		// .replaceAll("&lt;ef0267ef&gt;", "<img src=\"e01a\"/>")
		// .replaceAll("&lt;ef0268ef&gt;", "<img src=\"e134\"/>")
		// .replaceAll("&lt;ef0269ef&gt;", "<img src=\"e530\"/>")
		// .replaceAll("&lt;ef0270ef&gt;", "<img src=\"e529\"/>")
		// .replaceAll("&lt;ef0271ef&gt;", "<img src=\"e526\"/>")
		// .replaceAll("&lt;ef0272ef&gt;", "<img src=\"e52d\"/>")
		// .replaceAll("&lt;ef0273ef&gt;", "<img src=\"e521\"/>")
		// .replaceAll("&lt;ef0274ef&gt;", "<img src=\"e523\"/>")
		// .replaceAll("&lt;ef0275ef&gt;", "<img src=\"e52e\"/>")
		// .replaceAll("&lt;ef0276ef&gt;", "<img src=\"e055\"/>")
		// .replaceAll("&lt;ef0277ef&gt;", "<img src=\"e525\"/>")
		// .replaceAll("&lt;ef0278ef&gt;", "<img src=\"e10a\"/>")
		// .replaceAll("&lt;ef0279ef&gt;", "<img src=\"e522\"/>")
		// .replaceAll("&lt;ef0280ef&gt;", "<img src=\"e019\"/>")
		// .replaceAll("&lt;ef0281ef&gt;", "<img src=\"e054\"/>")
		// .replaceAll("&lt;ef0282ef&gt;", "<img src=\"e520\"/>")
		// .replaceAll("&lt;ef0283ef&gt;", "<img src=\"e306\"/>")
		// .replaceAll("&lt;ef0284ef&gt;", "<img src=\"e030\"/>")
		// .replaceAll("&lt;ef0285ef&gt;", "<img src=\"e304\"/>")
		// .replaceAll("&lt;ef0286ef&gt;", "<img src=\"e110\"/>")
		// .replaceAll("&lt;ef0287ef&gt;", "<img src=\"e032\"/>")
		// .replaceAll("&lt;ef0288ef&gt;", "<img src=\"e305\"/>")
		// .replaceAll("&lt;ef0289ef&gt;", "<img src=\"e303\"/>")
		// .replaceAll("&lt;ef0290ef&gt;", "<img src=\"e118\"/>")
		// .replaceAll("&lt;ef0291ef&gt;", "<img src=\"e447\"/>")
		// .replaceAll("&lt;ef0292ef&gt;", "<img src=\"e119\"/>")
		// .replaceAll("&lt;ef0293ef&gt;", "<img src=\"e307\"/>")
		// .replaceAll("&lt;ef0294ef&gt;", "<img src=\"e308\"/>")
		// .replaceAll("&lt;ef0295ef&gt;", "<img src=\"e444\"/>")
		// .replaceAll("&lt;ef0296ef&gt;", "<img src=\"e441\"/>")
		// .replaceAll("&lt;ef0297ef&gt;", "<img src=\"e21c\"/>")
		// .replaceAll("&lt;ef0298ef&gt;", "<img src=\"e21d\"/>")
		// .replaceAll("&lt;ef0299ef&gt;", "<img src=\"e21e\"/>")
		// .replaceAll("&lt;ef0300ef&gt;", "<img src=\"e21f\"/>")
		// .replaceAll("&lt;ef0301ef&gt;", "<img src=\"e220\"/>")
		// .replaceAll("&lt;ef0302ef&gt;", "<img src=\"e221\"/>")
		// .replaceAll("&lt;ef0303ef&gt;", "<img src=\"e222\"/>")
		// .replaceAll("&lt;ef0304ef&gt;", "<img src=\"e223\"/>")
		// .replaceAll("&lt;ef0305ef&gt;", "<img src=\"e224\"/>")
		// .replaceAll("&lt;ef0306ef&gt;", "<img src=\"e225\"/>")
		// .replaceAll("&lt;ef0307ef&gt;", "<img src=\"e210\"/>")
		// .replaceAll("&lt;ef0308ef&gt;", "<img src=\"e232\"/>")
		// .replaceAll("&lt;ef0309ef&gt;", "<img src=\"e233\"/>")
		// .replaceAll("&lt;ef0310ef&gt;", "<img src=\"e235\"/>")
		// .replaceAll("&lt;ef0311ef&gt;", "<img src=\"e234\"/>")
		// .replaceAll("&lt;ef0312ef&gt;", "<img src=\"e236\"/>")
		// .replaceAll("&lt;ef0313ef&gt;", "<img src=\"e237\"/>")
		// .replaceAll("&lt;ef0314ef&gt;", "<img src=\"e238\"/>")
		// .replaceAll("&lt;ef0315ef&gt;", "<img src=\"e239\"/>")
		// .replaceAll("&lt;ef0316ef&gt;", "<img src=\"e23b\"/>")
		// .replaceAll("&lt;ef0317ef&gt;", "<img src=\"e23a\"/>")
		// .replaceAll("&lt;ef0318ef&gt;", "<img src=\"e23d\"/>")
		// .replaceAll("&lt;ef0319ef&gt;", "<img src=\"e23c\"/>")
		// .replaceAll("&lt;ef0320ef&gt;", "<img src=\"e24d\"/>")
		// .replaceAll("&lt;ef0321ef&gt;", "<img src=\"e212\"/>")
		// .replaceAll("&lt;ef0322ef&gt;", "<img src=\"e24c\"/>")
		// .replaceAll("&lt;ef0323ef&gt;", "<img src=\"e213\"/>")
		// .replaceAll("&lt;ef0324ef&gt;", "<img src=\"e214\"/>")
		// .replaceAll("&lt;ef0325ef&gt;", "<img src=\"e507\"/>")
		// .replaceAll("&lt;ef0326ef&gt;", "<img src=\"e203\"/>")
		// .replaceAll("&lt;ef0327ef&gt;", "<img src=\"e20b\"/>")
		// .replaceAll("&lt;ef0328ef&gt;", "<img src=\"e22a\"/>")
		// .replaceAll("&lt;ef0329ef&gt;", "<img src=\"e22b\"/>")
		// .replaceAll("&lt;ef0330ef&gt;", "<img src=\"e226\"/>")
		// .replaceAll("&lt;ef0331ef&gt;", "<img src=\"e227\"/>")
		// .replaceAll("&lt;ef0332ef&gt;", "<img src=\"e22c\"/>")
		// .replaceAll("&lt;ef0333ef&gt;", "<img src=\"e22d\"/>")
		// .replaceAll("&lt;ef0334ef&gt;", "<img src=\"e215\"/>")
		// .replaceAll("&lt;ef0335ef&gt;", "<img src=\"e216\"/>")
		// .replaceAll("&lt;ef0336ef&gt;", "<img src=\"e217\"/>")
		// .replaceAll("&lt;ef0337ef&gt;", "<img src=\"e218\"/>")
		// .replaceAll("&lt;ef0338ef&gt;", "<img src=\"e228\"/>")
		// .replaceAll("&lt;ef0339ef&gt;", "<img src=\"e151\"/>")
		// .replaceAll("&lt;ef0340ef&gt;", "<img src=\"e138\"/>")
		// .replaceAll("&lt;ef0341ef&gt;", "<img src=\"e139\"/>")
		// .replaceAll("&lt;ef0342ef&gt;", "<img src=\"e13a\"/>")
		// .replaceAll("&lt;ef0343ef&gt;", "<img src=\"e208\"/>")
		// .replaceAll("&lt;ef0344ef&gt;", "<img src=\"e14f\"/>")
		// .replaceAll("&lt;ef0345ef&gt;", "<img src=\"e20a\"/>")
		// .replaceAll("&lt;ef0346ef&gt;", "<img src=\"e434\"/>")
		// .replaceAll("&lt;ef0347ef&gt;", "<img src=\"e309\"/>")
		// .replaceAll("&lt;ef0348ef&gt;", "<img src=\"e315\"/>")
		// .replaceAll("&lt;ef0349ef&gt;", "<img src=\"e30d\"/>")
		// .replaceAll("&lt;ef0350ef&gt;", "<img src=\"e207\"/>")
		// .replaceAll("&lt;ef0351ef&gt;", "<img src=\"e229\"/>")
		// .replaceAll("&lt;ef0352ef&gt;", "<img src=\"e206\"/>")
		// .replaceAll("&lt;ef0353ef&gt;", "<img src=\"e205\"/>")
		// .replaceAll("&lt;ef0354ef&gt;", "<img src=\"e204\"/>")
		// .replaceAll("&lt;ef0355ef&gt;", "<img src=\"e12e\"/>")
		// .replaceAll("&lt;ef0356ef&gt;", "<img src=\"e250\"/>")
		// .replaceAll("&lt;ef0357ef&gt;", "<img src=\"e251\"/>")
		// .replaceAll("&lt;ef0358ef&gt;", "<img src=\"e14a\"/>")
		// .replaceAll("&lt;ef0359ef&gt;", "<img src=\"e149\"/>")
		// .replaceAll("&lt;ef0360ef&gt;", "<img src=\"e23f\"/>")
		// .replaceAll("&lt;ef0361ef&gt;", "<img src=\"e240\"/>")
		// .replaceAll("&lt;ef0362ef&gt;", "<img src=\"e241\"/>")
		// .replaceAll("&lt;ef0363ef&gt;", "<img src=\"e242\"/>")
		// .replaceAll("&lt;ef0364ef&gt;", "<img src=\"e243\"/>")
		// .replaceAll("&lt;ef0365ef&gt;", "<img src=\"e244\"/>")
		// .replaceAll("&lt;ef0366ef&gt;", "<img src=\"e245\"/>")
		// .replaceAll("&lt;ef0367ef&gt;", "<img src=\"e246\"/>")
		// .replaceAll("&lt;ef0368ef&gt;", "<img src=\"e247\"/>")
		// .replaceAll("&lt;ef0369ef&gt;", "<img src=\"e248\"/>")
		// .replaceAll("&lt;ef0370ef&gt;", "<img src=\"e249\"/>")
		// .replaceAll("&lt;ef0371ef&gt;", "<img src=\"e24a\"/>")
		// .replaceAll("&lt;ef0372ef&gt;", "<img src=\"e24b\"/>")
		// .replaceAll("&lt;ef0373ef&gt;", "<img src=\"e23e\"/>")
		// .replaceAll("&lt;ef0374ef&gt;", "<img src=\"e532\"/>")
		// .replaceAll("&lt;ef0375ef&gt;", "<img src=\"e533\"/>")
		// .replaceAll("&lt;ef0376ef&gt;", "<img src=\"e534\"/>")
		// .replaceAll("&lt;ef0377ef&gt;", "<img src=\"e535\"/>")
		// .replaceAll("&lt;ef0378ef&gt;", "<img src=\"e21a\"/>")
		// .replaceAll("&lt;ef0379ef&gt;", "<img src=\"e219\"/>")
		// .replaceAll("&lt;ef0380ef&gt;", "<img src=\"e21b\"/>")
		// .replaceAll("&lt;ef0381ef&gt;", "<img src=\"e02f\"/>")
		// .replaceAll("&lt;ef0382ef&gt;", "<img src=\"e024\"/>")
		// .replaceAll("&lt;ef0383ef&gt;", "<img src=\"e025\"/>")
		// .replaceAll("&lt;ef0384ef&gt;", "<img src=\"e026\"/>")
		// .replaceAll("&lt;ef0385ef&gt;", "<img src=\"e027\"/>")
		// .replaceAll("&lt;ef0386ef&gt;", "<img src=\"e028\"/>")
		// .replaceAll("&lt;ef0387ef&gt;", "<img src=\"e029\"/>")
		// .replaceAll("&lt;ef0388ef&gt;", "<img src=\"e02a\"/>")
		// .replaceAll("&lt;ef0389ef&gt;", "<img src=\"e02b\"/>")
		// .replaceAll("&lt;ef0390ef&gt;", "<img src=\"e02c\"/>")
		// .replaceAll("&lt;ef0391ef&gt;", "<img src=\"e02d\"/>")
		// .replaceAll("&lt;ef0392ef&gt;", "<img src=\"e02e\"/>")
		// .replaceAll("&lt;ef0393ef&gt;", "<img src=\"e332\"/>")
		// .replaceAll("&lt;ef0394ef&gt;", "<img src=\"e333\"/>")
		// .replaceAll("&lt;ef0395ef&gt;", "<img src=\"e24e\"/>")
		// .replaceAll("&lt;ef0396ef&gt;", "<img src=\"e24f\"/>")
		// .replaceAll("&lt;ef0397ef&gt;", "<img src=\"e537\"/>")
		// .replaceAll("&lt;ef0398ef&gt;", "<img src=\"e036\"/>")
		// .replaceAll("&lt;ef0399ef&gt;", "<img src=\"e157\"/>")
		// .replaceAll("&lt;ef0400ef&gt;", "<img src=\"e038\"/>")
		// .replaceAll("&lt;ef0401ef&gt;", "<img src=\"e153\"/>")
		// .replaceAll("&lt;ef0402ef&gt;", "<img src=\"e155\"/>")
		// .replaceAll("&lt;ef0403ef&gt;", "<img src=\"e14d\"/>")
		// .replaceAll("&lt;ef0404ef&gt;", "<img src=\"e156\"/>")
		// .replaceAll("&lt;ef0405ef&gt;", "<img src=\"e501\"/>")
		// .replaceAll("&lt;ef0406ef&gt;", "<img src=\"e158\"/>")
		// .replaceAll("&lt;ef0407ef&gt;", "<img src=\"e43d\"/>")
		// .replaceAll("&lt;ef0408ef&gt;", "<img src=\"e037\"/>")
		// .replaceAll("&lt;ef0409ef&gt;", "<img src=\"e504\"/>")
		// .replaceAll("&lt;ef0410ef&gt;", "<img src=\"e44a\"/>")
		// .replaceAll("&lt;ef0411ef&gt;", "<img src=\"e146\"/>")
		// .replaceAll("&lt;ef0412ef&gt;", "<img src=\"e154\"/>")
		// .replaceAll("&lt;ef0413ef&gt;", "<img src=\"e505\"/>")
		// .replaceAll("&lt;ef0414ef&gt;", "<img src=\"e506\"/>")
		// .replaceAll("&lt;ef0415ef&gt;", "<img src=\"e122\"/>")
		// .replaceAll("&lt;ef0416ef&gt;", "<img src=\"e508\"/>")
		// .replaceAll("&lt;ef0417ef&gt;", "<img src=\"e509\"/>")
		// .replaceAll("&lt;ef0418ef&gt;", "<img src=\"e03b\"/>")
		// .replaceAll("&lt;ef0419ef&gt;", "<img src=\"e04d\"/>")
		// .replaceAll("&lt;ef0420ef&gt;", "<img src=\"e449\"/>")
		// .replaceAll("&lt;ef0421ef&gt;", "<img src=\"e44b\"/>")
		// .replaceAll("&lt;ef0422ef&gt;", "<img src=\"e51d\"/>")
		// .replaceAll("&lt;ef0423ef&gt;", "<img src=\"e44c\"/>")
		// .replaceAll("&lt;ef0424ef&gt;", "<img src=\"e124\"/>")
		// .replaceAll("&lt;ef0425ef&gt;", "<img src=\"e121\"/>")
		// .replaceAll("&lt;ef0426ef&gt;", "<img src=\"e433\"/>")
		// .replaceAll("&lt;ef0427ef&gt;", "<img src=\"e202\"/>")
		// .replaceAll("&lt;ef0428ef&gt;", "<img src=\"e135\"/>")
		// .replaceAll("&lt;ef0429ef&gt;", "<img src=\"e01c\"/>")
		// .replaceAll("&lt;ef0430ef&gt;", "<img src=\"e01d\"/>")
		// .replaceAll("&lt;ef0431ef&gt;", "<img src=\"e10d\"/>")
		// .replaceAll("&lt;ef0432ef&gt;", "<img src=\"e136\"/>")
		// .replaceAll("&lt;ef0433ef&gt;", "<img src=\"e42e\"/>")
		// .replaceAll("&lt;ef0434ef&gt;", "<img src=\"e01b\"/>")
		// .replaceAll("&lt;ef0435ef&gt;", "<img src=\"e15a\"/>")
		// .replaceAll("&lt;ef0436ef&gt;", "<img src=\"e159\"/>")
		// .replaceAll("&lt;ef0437ef&gt;", "<img src=\"e432\"/>")
		// .replaceAll("&lt;ef0438ef&gt;", "<img src=\"e430\"/>")
		// .replaceAll("&lt;ef0439ef&gt;", "<img src=\"e431\"/>")
		// .replaceAll("&lt;ef0440ef&gt;", "<img src=\"e42f\"/>")
		// .replaceAll("&lt;ef0441ef&gt;", "<img src=\"e01e\"/>")
		// .replaceAll("&lt;ef0442ef&gt;", "<img src=\"e039\"/>")
		// .replaceAll("&lt;ef0443ef&gt;", "<img src=\"e435\"/>")
		// .replaceAll("&lt;ef0444ef&gt;", "<img src=\"e01f\"/>")
		// .replaceAll("&lt;ef0445ef&gt;", "<img src=\"e125\"/>")
		// .replaceAll("&lt;ef0446ef&gt;", "<img src=\"e03a\"/>")
		// .replaceAll("&lt;ef0447ef&gt;", "<img src=\"e14e\"/>")
		// .replaceAll("&lt;ef0448ef&gt;", "<img src=\"e252\"/>")
		// .replaceAll("&lt;ef0449ef&gt;", "<img src=\"e137\"/>")
		// .replaceAll("&lt;ef0450ef&gt;", "<img src=\"e209\"/>")
		// .replaceAll("&lt;ef0451ef&gt;", "<img src=\"e133\"/>")
		// .replaceAll("&lt;ef0452ef&gt;", "<img src=\"e150\"/>")
		// .replaceAll("&lt;ef0453ef&gt;", "<img src=\"e320\"/>")
		// .replaceAll("&lt;ef0454ef&gt;", "<img src=\"e123\"/>")
		// .replaceAll("&lt;ef0455ef&gt;", "<img src=\"e132\"/>")
		// .replaceAll("&lt;ef0456ef&gt;", "<img src=\"e143\"/>")
		// .replaceAll("&lt;ef0457ef&gt;", "<img src=\"e50b\"/>")
		// .replaceAll("&lt;ef0458ef&gt;", "<img src=\"e514\"/>")
		// .replaceAll("&lt;ef0459ef&gt;", "<img src=\"e513\"/>")
		// .replaceAll("&lt;ef0460ef&gt;", "<img src=\"e50c\"/>")
		// .replaceAll("&lt;ef0461ef&gt;", "<img src=\"e50d\"/>")
		// .replaceAll("&lt;ef0462ef&gt;", "<img src=\"e511\"/>")
		// .replaceAll("&lt;ef0463ef&gt;", "<img src=\"e50f\"/>")
		// .replaceAll("&lt;ef0464ef&gt;", "<img src=\"e512\"/>")
		// .replaceAll("&lt;ef0465ef&gt;", "<img src=\"e510\"/>")
		// .replaceAll("&lt;ef0466ef&gt;", "<img src=\"e50e\"/>");
	}
}
