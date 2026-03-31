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
 * ケース11
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース11 受講生 勤怠直接編集 正常系")
public class Case11 {

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
	@DisplayName("テスト05 すべての研修日程の勤怠情報を正しく更新し勤怠管理画面に遷移")
	void test05() {

		// 10/1の勤怠情報入力（9:00-12:00、体調不良のため早退）
		Select endHour = new Select(webDriver.findElement(By.id("endHour0")));
		endHour.selectByVisibleText("12");
		WebElement reason1001 = webDriver.findElement(By.name("attendanceList[0].note"));
		reason1001.clear();
		reason1001.sendKeys("体調不良");

		// 10/2の勤怠情報入力（14:00-15:00面談のため中抜け）
		Select blankTime = new Select(webDriver.findElement(By.name("attendanceList[1].blankTime")));
		blankTime.selectByVisibleText("1時間");
		WebElement reason1002 = webDriver.findElement(By.name("attendanceList[1].note"));
		reason1002.clear();
		reason1002.sendKeys("14:00-15:00 面談");

		scrollTo("1000");

		// 「更新」ボタンをクリック
		webDriver.findElement(By.xpath("//input[@value = '更新']")).click();

		// ポップアップ
		webDriver.switchTo().alert().accept();

		pageLoadTimeout(5);

		visibilityTimeout(By.tagName("h2"), 5);

		// 検証：勤怠管理画面への遷移、更新の確認
		WebElement completeMsg = webDriver.findElement(By.className("alert"));
		WebElement startTime1001 = webDriver.findElement(By.xpath("//tbody/tr[1]/td[3]"));
		WebElement endTime1001 = webDriver.findElement(By.xpath("//tbody/tr[1]/td[4]"));
		WebElement updateReason1001 = webDriver.findElement(By.xpath("//tbody/tr[1]/td[7]"));

		assertTrue(completeMsg.isDisplayed());
		assertEquals("09:00", startTime1001.getText());
		assertEquals("12:00", endTime1001.getText());
		assertEquals("体調不良", updateReason1001.getText());

		WebElement startTime1002 = webDriver.findElement(By.xpath("//tbody//tr[2]/td[3]"));
		WebElement endTime1002 = webDriver.findElement(By.xpath("//tbody/tr[2]/td[4]"));
		WebElement blankTime1002 = webDriver.findElement(By.xpath("//tbody/tr[2]/td[5]"));
		WebElement updateReason1002 = webDriver.findElement(By.xpath("//tbody/tr[2]/td[7]"));

		assertEquals("09:00", startTime1002.getText());
		assertEquals("18:00", endTime1002.getText());
		assertEquals("01:00", blankTime1002.getText());
		assertEquals("14:00-15:00 面談", updateReason1002.getText());

		assertEquals("勤怠管理", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

}
