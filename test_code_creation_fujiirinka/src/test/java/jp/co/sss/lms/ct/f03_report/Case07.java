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
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		scrollTo("200");

		// 「詳細」ボタンをクリック
		List<WebElement> submitButtonList = webDriver.findElements(By.cssSelector("input[type = 'submit']"));
		submitButtonList.get(3).click();

		// 検証：セクション詳細画面への遷移
		assertEquals("関係演算子、条件分岐、繰り返し 2022年10月6日", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		// 「日報【デモ】を提出する」ボタンをクリック
		WebElement submitBtn = webDriver.findElement(By.cssSelector("input[type = 'submit']"));
		submitBtn.click();

		// 検証：レポート登録画面への遷移
		assertEquals("日報【デモ】 2022年10月6日", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {

		// 報告内容の入力、クリック
		WebElement reportForm = webDriver.findElement(By.className("form-control"));
		String report = "日報提出テストです。";
		WebElement submitBtn = webDriver.findElement(By.cssSelector("button[type = 'submit']"));

		reportForm.clear();
		reportForm.sendKeys(report);
		submitBtn.click();

		// 検証：確認ボタン名の更新を確認
		WebElement checkBtn = webDriver.findElement(By.cssSelector("input[type = 'submit']"));
		assertEquals("提出済み日報【デモ】を確認する", checkBtn.getAttribute("value"));
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

}
