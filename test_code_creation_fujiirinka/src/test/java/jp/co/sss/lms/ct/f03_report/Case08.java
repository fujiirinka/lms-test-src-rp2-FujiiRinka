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

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		// 「詳細」ボタンをクリック
		List<WebElement> submitButtonList = webDriver.findElements(By.cssSelector("input[type = 'submit']"));
		submitButtonList.get(1).click();

		// 検証：セクション詳細画面への遷移
		assertEquals("アルゴリズム、フローチャート 2022年10月2日", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		scrollTo("500");

		// 「提出済み週報【デモ】を確認する」ボタンをクリック
		WebElement checkBtn = webDriver.findElement(By.cssSelector("input[value = '提出済み週報【デモ】を確認する']"));
		checkBtn.click();

		// 検証：レポート登録画面への遷移
		assertEquals("週報【デモ】 2022年10月2日", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		// エビデンスの取得(1)
		getEvidence(new Object() {
		}, "upper");

		scrollTo("500");

		// エビデンスの取得(2):test05 ページ下部比較のため
		getEvidence(new Object() {
		}, "lower");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {

		// 報告内容の修正
		WebElement impression = webDriver.findElement(By.id("content_1"));
		String fixImpression = "週報修正テストです。";
		WebElement lookBack = webDriver.findElement(By.id("content_2"));
		String fixLookBack = "来週もよろしくお願いします。";

		impression.clear();
		impression.sendKeys(fixImpression);
		lookBack.clear();
		lookBack.sendKeys(fixLookBack);

		// エビデンスの取得(1)
		getEvidence(new Object() {
		}, "fixReport");

		// 「提出する」ボタンをクリック
		WebElement submitBtn = webDriver.findElement(By.cssSelector("button[type = 'submit']"));
		submitBtn.click();

		pageLoadTimeout(10);

		// 検証：セクション詳細画面への遷移
		assertEquals("アルゴリズム、フローチャート 2022年10月2日", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		// エビデンスの取得(2):
		getEvidence(new Object() {
		}, "pageTransition");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {

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
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {

		scrollTo("500");

		// 「詳細」ボタンをクリック
		List<WebElement> submitButtonList = webDriver.findElements(By.cssSelector("input[value = '詳細']"));
		submitButtonList.get(2).click();

		// 検証：レポートの詳細画面への遷移
		assertEquals("週報【デモ】 2022年10月2日", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("レポート詳細 | LMS", webDriver.getTitle());

		// 検証：報告内容更新の確認
		WebElement updateImpression = webDriver.findElement(By.xpath("//td[contains(text(), '修正テスト')]"));
		WebElement updateLookBack = webDriver.findElement(By.xpath("//td[contains(text(), '来週')]"));

		assertEquals("週報修正テストです。", updateImpression.getText());
		assertEquals("来週もよろしくお願いします。", updateLookBack.getText());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

}
