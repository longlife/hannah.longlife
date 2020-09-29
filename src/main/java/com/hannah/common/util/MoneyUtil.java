package com.hannah.common.util;

import java.math.BigDecimal;

/**
 * 金额处理：金额大小写互换（繁体字不支持）
 * 
 * 附：正确填写票据和结算凭证的基本规定
 * 银行、单位和个人填写的各种票据和结算凭证是办理支付结算和现金收付的重要依据，直接关系到支付结算的准确、及时和安全。
 * 票据和结算凭证是银行、单位和个人凭以记载账务的会计凭证，是记载经济业务和明确经济责任的一种书面证明。
 * 因此，填写票据和结算凭证，必须做到标准化、规范化，要要素齐全、数字正确、字迹清晰、不错漏、不潦草，防止涂改。
 * 一、中文大写金额数字应用正楷或行书填写，如
 * 壹、贰、叁、肆、伍、陆、柒、捌、玖、拾、佰、仟、万、亿、元、角、分、零、整（正）等字样。
 * 不得用一、二（两）、三、四、五、六、七、八、九、十、念、毛、另（或0）填写，不得自造简化字。
 * 如果金额数字书写中使用繁体字，如貳、陸、億、萬、圓的，也应受理。
 * 二、中文大写金额数字到“元”为止的，在“元”之后，应写“整”（或“正”）字，在“角”之后可以不写“整”（或“正”）字。
 * 大写金额数字有“分”的，“分”后面不写“整”（或“正”）字。
 * 三、中文大写金额数字前应标明“人民币”字样，大写金额数字应紧接“人民币”字样填写，不得留有空白。
 * 大写金额数字前未印“人民币”字样的，应加填“人民币”三字。
 * 在票据和结算凭证大写金额栏内不得预印固定的“仟、佰、拾、万、仟、伯、拾、元、角、分”字样。
 * 四、阿拉伯小写金额数字中有“0”时，中文大写应按照汉语语言规律、金额数字构成和防止涂改的要求进行书写。 举例如下：
 * （一）阿拉伯数字中间有“O”时，中文大写金额要写“零”字。如￥1,409.50，应写成人民币壹仟肆佰零玖元伍角整。
 * （二）阿拉伯数字中间连续有几个“0”时，中文大写金额中间可以只写一个“零”字。 如￥6,007.14，应写成人民币陆仟零柒元壹角肆分。
 * （三）阿拉伯金额数字万位或元位是“0”，或者数字中间连续有几个“0”，万位、元位也是“0’，但千位、角位不是“0”时，
 * 中文大写金额中可以只写一个零字，也可以不写“零”字。
 * 如￥1,680.32，应写成人民币壹仟陆佰捌拾元零叁角贰分，或者写成人民币壹仟陆佰捌拾元叁角贰分；
 * 又如￥107,000.53，应写成人民币壹拾万柒仟元零伍角叁分，或者写成人民币壹拾万零柒仟元伍角叁分。
 * （四）阿拉伯金额数字角位是“0”，而分位不是“0”时，中文大写金额“元”后面应写“零”字。如￥16,409.02，
 * 应写成人民币壹万陆仟肆佰零玖元零贰分；又如￥325.04，应写成人民币叁佰贰拾伍元零肆分。
 * 五、阿拉伯小写金额数字前面，均应填写入民币符号“￥”（或草写：）。 阿拉伯小写金额数字要认真填写，不得连写分辨不清。
 * 六、票据的出票日期必须使用中文大写。
 * 为防止变造票据的出禀日期，在填写月、日时，月为壹、贰和壹拾的，日为壹至玖和壹拾、贰拾和叁抬的，应在其前加“零”；
 * 日为抬壹至拾玖的，应在其前加“壹”。如1月15日，应写成零壹月壹拾伍日。再如10月20日，应写成零壹拾月零贰拾日。
 * 七、票据出票日期使用小写填写的，银行不予受理。大写日期未按要求规范填写的，银行可予受理，但由此造成损失的，由出票入自行承担。
 * @author longrm
 * @date 2012-3-26
 */
public class MoneyUtil {
		
	public final static char[] MONEY_UNIT = {'元', '角', '分'};
		
	/**
	 * 将位转为计量单位
	 * @param b
	 * @return
	 */
	public static char bitToUnit(int b) {
		// 元（10^0）、角（10^-1）、分（10^-2）
		if (b <= 0)
			return MONEY_UNIT[-b];
		// 金额过拾（10^b）
		else
			return NumberUtil.bitToUnit(b);
	}
	
	/**
	 * 将计量单位转为位
	 * @param u
	 * @return
	 */
	public static int unitToBit(char u, int priorBit) {
		// 元、角、分
		for (int i=0; i<MONEY_UNIT.length; i++) {
			if (u == MONEY_UNIT[i])
				return -i;
		}
		// 金额过拾
		return NumberUtil.unitToBit(u, priorBit);
	}
	
	/**
	 * 将数字金额转换成中文大写
	 * @param money 要转换的数字金额
	 * @return 人民币大写
	 */
	public static String toUpperCase(BigDecimal money) {
		String result = "";
		
		String str = String.valueOf(money.abs().longValue() * 10 * 10);
		for (int i = 0; i < str.length(); i++) {
			int n = str.charAt(str.length() - 1 - i) - '0';	// 个位、十位、百位...开始取数
			result = NumberUtil.digitToUpperCase(n) + "" + bitToUnit(i - 2) + result;
		}
		
		result = result.replaceAll("零仟", "零");
		result = result.replaceAll("零佰", "零");
		result = result.replaceAll("零拾", "零");
		result = result.replaceAll("零亿", "亿");
		result = result.replaceAll("零万", "万");
		result = result.replaceAll("零元", "元");
		result = result.replaceAll("零角", "零");
		result = result.replaceAll("零分", "零");
		
		result = result.replaceAll("零零", "零");
		result = result.replaceAll("零亿", "亿");
		result = result.replaceAll("零零", "零");
		result = result.replaceAll("零万", "万");
		result = result.replaceAll("零零", "零");
		result = result.replaceAll("零元", "元");
		result = result.replaceAll("亿万", "亿");
		
		result = result.replaceAll("零$", "");
		result = result.replaceAll("元$", "元整");
		result = result.replaceAll("角$", "角整");
		
		return (money.compareTo(BigDecimal.ZERO)<0 ? "负" : "") + result;
	}
	
	/**
	 * 将人民币大写金额转为小写（繁体字不支持）
	 * @param upperMoney
	 * @return
	 */
	public static BigDecimal toLowerCase(String upperMoney) {
		int pointIndex = upperMoney.indexOf("元");
		if (pointIndex == -1)
			throw new RuntimeException("非法的人民币大写格式！");
		
		int direction = upperMoney.startsWith("负") ? -1 : 1;
		// 整数金额
		String upperInteger = upperMoney.substring(0, pointIndex);
		BigDecimal money = NumberUtil.toLowerCase(upperInteger).abs();
		
		// 小数金额
		String upperDecimal = upperMoney.substring(pointIndex + 1).replaceAll("整", "");
		int priorBit = 0;
		for (int i = upperDecimal.length()-1; i>=0; i--) {
			// 计量单位
			char u = upperDecimal.charAt(i);
			int bit = unitToBit(u, priorBit);
			if (bit == NumberUtil.ILLEGAL)
				throw new RuntimeException("非法的人民币大写格式！");
			// 数字
			char up = upperDecimal.charAt(--i);
			int number = NumberUtil.digitToLowerCase(up);
			if (number == NumberUtil.ILLEGAL)
				throw new RuntimeException("非法的人民币大写格式！");
			// 采用BigDecimal的string构造器计算，精确
			money = money.add(new BigDecimal(String.valueOf(number * Math.pow(10, bit))));
			priorBit = bit;
		}
		
		return money.multiply(new BigDecimal(direction));
	}
	
}
