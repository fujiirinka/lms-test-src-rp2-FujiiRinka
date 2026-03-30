package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {

		// 「ようこそ受講生AA2さん」リンクをクリック
		WebElement userLink = webDriver.findElement(By.linkText("ようこそ受講生ＡＡ２さん"));
		userLink.click();

		// 検証：ユーザー詳細画面への遷移
		assertEquals("ユーザー詳細", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("ユーザー詳細", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		scrollTo("500");

		// 「修正する」ボタンをクリック
		List<WebElement> submitButtonList = webDriver.findElements(By.cssSelector("input[value = '修正する']"));
		submitButtonList.get(2).click();

		// 検証：レポートの詳細画面への遷移
		assertEquals("週報【デモ】 2022年10月2日", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {

		// 報告内容の修正（学習項目を消去）
		WebElement ErrTopics = webDriver.findElement(By.id("intFieldName_0"));
		ErrTopics.clear();

		scrollTo("500");

		visibilityTimeout(By.cssSelector("button[type = 'submit']"), 5);

		// 「提出する」ボタンをクリック
		WebElement submitBtn = webDriver.findElement(By.cssSelector("button[type = 'submit']"));
		submitBtn.click();

		pageLoadTimeout(5);

		// 検証：エラー表示の確認
		WebElement errBox = webDriver.findElement(By.className("errorInput"));
		assertTrue(errBox.isDisplayed());
		assertEquals("週報【デモ】 2022年10月2日", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {

		// test05で消去した学習項目を再入力
		WebElement topics = webDriver.findElement(By.id("intFieldName_0"));
		String reTopics = "ITリテラシー①";
		topics.sendKeys(reTopics);

		// 報告内容の修正（理解度を消去）
		Select errSelect = new Select(webDriver.findElement(By.id("intFieldValue_0")));
		errSelect.selectByIndex(0);

		scrollTo("500");

		visibilityTimeout(By.cssSelector("button[type = 'submit']"), 5);

		// 「提出する」ボタンをクリック
		WebElement submitBtn = webDriver.findElement(By.cssSelector("button[type = 'submit']"));
		submitBtn.click();

		pageLoadTimeout(5);

		// 検証：エラー表示の確認
		WebElement errBox = webDriver.findElement(By.className("errorInput"));
		assertTrue(errBox.isDisplayed());
		assertEquals("週報【デモ】 2022年10月2日", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {

		// test06で消去した理解度を再入力
		Select reSelect = new Select(webDriver.findElement(By.id("intFieldValue_0")));
		reSelect.selectByIndex(1);

		// 報告内容の修正（目標の達成度を入力)
		WebElement achievementLevel = webDriver.findElement(By.id("content_0"));
		achievementLevel.clear();
		achievementLevel.sendKeys("あいうえお");

		scrollTo("400");

		visibilityTimeout(By.cssSelector("button[type = 'submit']"), 5);

		// 「提出する」ボタンをクリック
		WebElement submitBtn = webDriver.findElement(By.cssSelector("button[type = 'submit']"));
		submitBtn.click();

		pageLoadTimeout(5);

		// 検証：エラー表示の確認
		WebElement errBox = webDriver.findElement(By.className("errorInput"));
		assertTrue(errBox.isDisplayed());
		assertEquals("週報【デモ】 2022年10月2日", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {

		// 報告内容の修正（目標の達成度を入力)
		WebElement achievementLevel = webDriver.findElement(By.id("content_0"));
		achievementLevel.clear();
		achievementLevel.sendKeys("11");

		scrollTo("500");

		visibilityTimeout(By.cssSelector("button[type = 'submit']"), 5);

		// 「提出する」ボタンをクリック
		WebElement submitBtn = webDriver.findElement(By.cssSelector("button[type = 'submit']"));
		submitBtn.click();

		pageLoadTimeout(5);

		// 検証：エラー表示の確認
		WebElement errBox = webDriver.findElement(By.className("errorInput"));
		assertTrue(errBox.isDisplayed());
		assertEquals("週報【デモ】 2022年10月2日", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {

		// 報告内容の修正（目標の達成度・所感を消去)
		WebElement achievementLevel = webDriver.findElement(By.id("content_0"));
		WebElement impression = webDriver.findElement(By.id("content_1"));
		achievementLevel.clear();
		impression.clear();

		scrollTo("1000");

		visibilityTimeout(By.cssSelector("button[type = 'submit']"), 5);

		// 「提出する」ボタンをクリック
		WebElement submitBtn = webDriver.findElement(By.cssSelector("button[type = 'submit']"));
		submitBtn.click();

		pageLoadTimeout(5);

		scrollTo("800");

		// 検証：エラー表示の確認
		WebElement errBox = webDriver.findElement(By.className("errorInput"));
		assertTrue(errBox.isDisplayed());
		assertEquals("週報【デモ】 2022年10月2日", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});

		// 目標の達成度を再入力
		WebElement reAchievementLevel = webDriver.findElement(By.id("content_0"));
		reAchievementLevel.clear();
		reAchievementLevel.sendKeys("1");
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {

		// 報告内容の修正（所感・一週間の振り返りを入力)
		WebElement impression = webDriver.findElement(By.id("content_1"));
		WebElement lookBack = webDriver.findElement(By.id("content_2"));
		String errReport = "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";

		impression.clear();
		lookBack.clear();
		impression.sendKeys(errReport);
		lookBack.sendKeys(errReport);

		scrollTo("800");

		visibilityTimeout(By.cssSelector("button[type = 'submit']"), 5);

		// 「提出する」ボタンをクリック
		WebElement submitBtn = webDriver.findElement(By.cssSelector("button[type = 'submit']"));
		submitBtn.click();

		pageLoadTimeout(5);

		scrollTo("1000");

		// 検証：エラー表示の確認
		WebElement errBox = webDriver.findElement(By.className("errorInput"));
		assertTrue(errBox.isDisplayed());
		assertEquals("週報【デモ】 2022年10月2日", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

}
