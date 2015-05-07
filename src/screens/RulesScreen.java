package screens;

import util.MainReturner;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;

/**
 * User: SMironov Date: 26.03.2004 Time: 17:30:03
 * @author Mediaspectrum, Inc.
 */
class RulesScreen extends Form {
	private static final String RULES_TEXT = "Задача игры - набрать максимум клеток своего цвета. " +
			"Выбор позиции кнопками 1, 2, 3, 4, 6, 7, 8 и 9. " +
			"Новая клетка вставляется кнопкой 5. Клетки соперника, оказавшиеся между клетками другого цвета, " +
			"меняются в цвете.";

	public RulesScreen(Display disp) {
		super(MainScreen.RULES);
		append(new StringItem(null, RULES_TEXT));
		new MainReturner(this);
		disp.setCurrent(this);
	}
}
