package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト 勤怠管理機能
 * ケース12
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース12 受講生 勤怠直接編集 入力チェック")
public class Case12 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {

		// URLにアクセス
		goTo("http://localhost:8080/lms");

		// 検証：トップページ
		assertEquals("ログイン | LMS", webDriver.getTitle());
		assertEquals("ログイン", webDriver.findElement(By.tagName("h2")).getText());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		// 初回ログイン済みのID/PWを入力、クリック
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA02");
		webDriver.findElement(By.id("password")).sendKeys("StudentAA02");
		webDriver.findElement(By.className("btn")).click();

		pageLoadTimeout(10);

		// 検証：コース詳細画面への遷移
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {

		// 「勤怠」リンクをクリック
		WebElement attendanceLink = webDriver.findElement(By.linkText("勤怠"));
		attendanceLink.click();

		visibilityTimeout(By.tagName("h2"), 5);

		// 検証：勤怠管理画面への遷移
		assertEquals("勤怠管理", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {

		// 「勤怠情報を直接編集する」リンクをクリック
		WebElement editAttendanceLink = webDriver.findElement(By.linkText("勤怠情報を直接編集する"));
		editAttendanceLink.click();

		visibilityTimeout(By.tagName("h2"), 5);

		// 検証：勤怠管理画面への遷移
		assertEquals("勤怠管理", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 不適切な内容で修正してエラー表示：出退勤の（時）と（分）のいずれかが空白")
	void test05() {

		// 10/5の勤怠情報を入力（09:xx-18:00）
		Select startMinute = new Select(webDriver.findElement(By.id("startMinute2")));
		startMinute.selectByVisibleText("");

		scrollTo("1000");

		// 「更新」ボタンをクリック
		webDriver.findElement(By.xpath("//input[@value = '更新']")).click();

		// ポップアップ
		webDriver.switchTo().alert().accept();

		// 検証：エラー表示の確認
		WebElement errMsg = webDriver.findElement(By.className("help-inline"));
		assertEquals("* 出勤時間が正しく入力されていません。", errMsg.getText());

		WebElement errBox = webDriver
				.findElement(
						By.xpath("//select[@id = 'startMinute2' and contains(@class, 'errorInput')]"));
		assertTrue(errBox.isDisplayed());
		assertEquals("勤怠管理", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});

		// 10/5の勤怠情報を再入力（09:00-18:00）
		//		Select reStartMinute = new Select(webDriver.findElement(By.id("startMinute2")));
		//		reStartMinute.selectByVisibleText("00");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正してエラー表示：出勤が空白で退勤に入力あり")
	void test06() {

		// 10/5の勤怠情報を入力（xx:xx-18:00）
		Select startHour = new Select(webDriver.findElement(By.id("startHour2")));
		startHour.selectByVisibleText("");
		Select startMinute = new Select(webDriver.findElement(By.id("startMinute2")));
		startMinute.selectByVisibleText("");

		scrollTo("1000");

		// 「更新」ボタンをクリック
		webDriver.findElement(By.xpath("//input[@value = '更新']")).click();

		// ポップアップ
		webDriver.switchTo().alert().accept();

		// 検証：エラー表示の確認
		WebElement errMsg = webDriver.findElement(By.className("help-inline"));
		assertEquals("* 出勤情報がないため退勤情報を入力出来ません。", errMsg.getText());

		WebElement errStartHourBox = webDriver
				.findElement(
						By.xpath("//select[@id = 'startHour2' and contains(@class, 'errorInput')]"));
		WebElement errStartMinuteBox = webDriver
				.findElement(
						By.xpath("//select[@id = 'startMinute2' and contains(@class, 'errorInput')]"));
		assertTrue(errStartHourBox.isDisplayed());
		assertTrue(errStartMinuteBox.isDisplayed());
		assertEquals("勤怠管理", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});

		// 10/5の勤怠情報を再入力（09:00-18:00）
		Select reStartHour = new Select(webDriver.findElement(By.id("startHour2")));
		reStartHour.selectByVisibleText("09");
		Select reStartMinute = new Select(webDriver.findElement(By.id("startMinute2")));
		reStartMinute.selectByVisibleText("00");
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正してエラー表示：出勤が退勤よりも遅い時間")
	void test07() {

		// 10/5の勤怠情報を入力（18:00-09:00）
		Select startHour = new Select(webDriver.findElement(By.id("startHour2")));
		startHour.selectByVisibleText("18");
		Select endHour = new Select(webDriver.findElement(By.id("endHour2")));
		endHour.selectByVisibleText("09");

		scrollTo("1000");

		// 「更新」ボタンをクリック
		webDriver.findElement(By.xpath("//input[@value = '更新']")).click();

		// ポップアップ
		webDriver.switchTo().alert().accept();

		// 検証：エラー表示の確認
		WebElement errMsg = webDriver.findElement(By.className("help-inline"));
		assertEquals("* 退勤時刻[2]は出勤時刻[2]より後でなければいけません。", errMsg.getText());

		WebElement errEndHourBox = webDriver
				.findElement(
						By.xpath("//select[@id = 'endHour2' and contains(@class, 'errorInput')]"));
		WebElement errEndMinuteBox = webDriver
				.findElement(
						By.xpath("//select[@id = 'endMinute2' and contains(@class, 'errorInput')]"));
		assertTrue(errEndHourBox.isDisplayed());
		assertTrue(errEndMinuteBox.isDisplayed());
		assertEquals("勤怠管理", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});

		// 10/5の勤怠情報を再入力（09:00-18:00）
		Select reStartHour = new Select(webDriver.findElement(By.id("startHour2")));
		reStartHour.selectByVisibleText("09");
		Select reEndHour = new Select(webDriver.findElement(By.id("endHour2")));
		reEndHour.selectByVisibleText("18");
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正してエラー表示：出退勤時間を超える中抜け時間")
	void test08() {

		// 10/5の勤怠情報を入力（09:00-12:00 中抜け：5時間）
		Select endHour = new Select(webDriver.findElement(By.id("endHour2")));
		endHour.selectByVisibleText("12");
		Select blankTime = new Select(webDriver.findElement(By.name("attendanceList[2].blankTime")));
		blankTime.selectByVisibleText("5時間");

		scrollTo("1000");

		// 「更新」ボタンをクリック
		webDriver.findElement(By.xpath("//input[@value = '更新']")).click();

		// ポップアップ
		webDriver.switchTo().alert().accept();

		// 検証：エラー表示の確認
		WebElement errMsg = webDriver.findElement(By.className("help-inline"));
		assertEquals("* 中抜け時間が勤務時間を超えています。", errMsg.getText());

		WebElement errBlankTimeBox = webDriver
				.findElement(
						By.xpath("//select[@name = 'attendanceList[2].blankTime' and contains(@class, 'errorInput')]"));
		assertTrue(errBlankTimeBox.isDisplayed());
		assertEquals("勤怠管理", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});

		// 10/5の勤怠情報を再入力（09:00-18:00）
		Select reEndHour = new Select(webDriver.findElement(By.id("endHour2")));
		reEndHour.selectByVisibleText("18");
		Select reBlankTime = new Select(webDriver.findElement(By.name("attendanceList[2].blankTime")));
		reBlankTime.selectByVisibleText("");
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正してエラー表示：備考が100文字超")
	void test09() {

		// 10/5の勤怠情報を入力（09:00-18:00 備考：100文字超）
		WebElement reason = webDriver.findElement(By.name("attendanceList[2].note"));
		String errReason = "fVCNEyd9gHreaoppFIJ4CmtzhzGpVHtzZtbtMrINNrNs208fHTCrUPHvqYoEPeZ0VC5UVnby1c7NFFlqOvGiuaTuYzkjauXM5hpew";
		reason.sendKeys(errReason);

		scrollTo("1000");

		// 「更新」ボタンをクリック
		webDriver.findElement(By.xpath("//input[@value = '更新']")).click();

		// ポップアップ
		webDriver.switchTo().alert().accept();

		// 検証：エラー表示の確認
		WebElement errMsg = webDriver.findElement(By.className("help-inline"));
		assertEquals("* 備考の長さが最大値(100)を超えています。", errMsg.getText());

		WebElement errReasonBox = webDriver
				.findElement(
						By.xpath("//input[@name = 'attendanceList[2].note' and contains(@class, 'errorInput')]"));
		assertTrue(errReasonBox.isDisplayed());
		assertEquals("勤怠管理", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

}
