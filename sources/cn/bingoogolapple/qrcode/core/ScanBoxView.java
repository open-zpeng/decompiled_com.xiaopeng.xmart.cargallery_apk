package cn.bingoogolapple.qrcode.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
/* loaded from: classes.dex */
public class ScanBoxView extends View {
    private int mAnimDelayTime;
    private int mAnimTime;
    private String mBarCodeTipText;
    private int mBarcodeRectHeight;
    private int mBorderColor;
    private int mBorderSize;
    private int mCornerColor;
    private int mCornerLength;
    private int mCornerSize;
    private Drawable mCustomGridScanLineDrawable;
    private Drawable mCustomScanLineDrawable;
    private Rect mFramingRect;
    private Bitmap mGridScanLineBitmap;
    private float mGridScanLineBottom;
    private float mGridScanLineRight;
    private float mHalfCornerSize;
    private boolean mIsBarcode;
    private boolean mIsCenterVertical;
    private boolean mIsOnlyDecodeScanBoxArea;
    private boolean mIsScanLineReverse;
    private boolean mIsShowDefaultGridScanLineDrawable;
    private boolean mIsShowDefaultScanLineDrawable;
    private boolean mIsShowTipBackground;
    private boolean mIsShowTipTextAsSingleLine;
    private boolean mIsTipTextBelowRect;
    private int mMaskColor;
    private int mMoveStepDistance;
    private Bitmap mOriginBarCodeGridScanLineBitmap;
    private Bitmap mOriginBarCodeScanLineBitmap;
    private Bitmap mOriginQRCodeGridScanLineBitmap;
    private Bitmap mOriginQRCodeScanLineBitmap;
    private Paint mPaint;
    private String mQRCodeTipText;
    private int mRectHeight;
    private int mRectWidth;
    private Bitmap mScanLineBitmap;
    private int mScanLineColor;
    private float mScanLineLeft;
    private int mScanLineMargin;
    private int mScanLineSize;
    private float mScanLineTop;
    private int mTipBackgroundColor;
    private int mTipBackgroundRadius;
    private TextPaint mTipPaint;
    private String mTipText;
    private int mTipTextColor;
    private int mTipTextMargin;
    private int mTipTextSize;
    private StaticLayout mTipTextSl;
    private int mToolbarHeight;
    private int mTopOffset;

    public ScanBoxView(Context context) {
        super(context);
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mMaskColor = Color.parseColor("#33FFFFFF");
        this.mCornerColor = -1;
        this.mCornerLength = BGAQRCodeUtil.dp2px(context, 20.0f);
        this.mCornerSize = BGAQRCodeUtil.dp2px(context, 3.0f);
        this.mScanLineSize = BGAQRCodeUtil.dp2px(context, 1.0f);
        this.mScanLineColor = -1;
        this.mTopOffset = BGAQRCodeUtil.dp2px(context, 90.0f);
        this.mRectWidth = BGAQRCodeUtil.dp2px(context, 200.0f);
        this.mBarcodeRectHeight = BGAQRCodeUtil.dp2px(context, 140.0f);
        this.mScanLineMargin = 0;
        this.mIsShowDefaultScanLineDrawable = false;
        this.mCustomScanLineDrawable = null;
        this.mScanLineBitmap = null;
        this.mBorderSize = BGAQRCodeUtil.dp2px(context, 1.0f);
        this.mBorderColor = -1;
        this.mAnimTime = 1000;
        this.mIsCenterVertical = false;
        this.mToolbarHeight = 0;
        this.mIsBarcode = false;
        this.mMoveStepDistance = BGAQRCodeUtil.dp2px(context, 2.0f);
        this.mTipText = null;
        this.mTipTextSize = BGAQRCodeUtil.sp2px(context, 14.0f);
        this.mTipTextColor = -1;
        this.mIsTipTextBelowRect = false;
        this.mTipTextMargin = BGAQRCodeUtil.dp2px(context, 20.0f);
        this.mIsShowTipTextAsSingleLine = false;
        this.mTipBackgroundColor = Color.parseColor("#22000000");
        this.mIsShowTipBackground = false;
        this.mIsScanLineReverse = false;
        this.mIsShowDefaultGridScanLineDrawable = false;
        TextPaint textPaint = new TextPaint();
        this.mTipPaint = textPaint;
        textPaint.setAntiAlias(true);
        this.mTipBackgroundRadius = BGAQRCodeUtil.dp2px(context, 4.0f);
        this.mIsOnlyDecodeScanBoxArea = false;
    }

    public void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.QRCodeView);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            initCustomAttr(typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();
        afterInitCustomAttrs();
    }

    private void initCustomAttr(int attr, TypedArray typedArray) {
        if (attr == R.styleable.QRCodeView_qrcv_topOffset) {
            this.mTopOffset = typedArray.getDimensionPixelSize(attr, this.mTopOffset);
        } else if (attr == R.styleable.QRCodeView_qrcv_cornerSize) {
            this.mCornerSize = typedArray.getDimensionPixelSize(attr, this.mCornerSize);
        } else if (attr == R.styleable.QRCodeView_qrcv_cornerLength) {
            this.mCornerLength = typedArray.getDimensionPixelSize(attr, this.mCornerLength);
        } else if (attr == R.styleable.QRCodeView_qrcv_scanLineSize) {
            this.mScanLineSize = typedArray.getDimensionPixelSize(attr, this.mScanLineSize);
        } else if (attr == R.styleable.QRCodeView_qrcv_rectWidth) {
            this.mRectWidth = typedArray.getDimensionPixelSize(attr, this.mRectWidth);
        } else if (attr == R.styleable.QRCodeView_qrcv_maskColor) {
            this.mMaskColor = typedArray.getColor(attr, this.mMaskColor);
        } else if (attr == R.styleable.QRCodeView_qrcv_cornerColor) {
            this.mCornerColor = typedArray.getColor(attr, this.mCornerColor);
        } else if (attr == R.styleable.QRCodeView_qrcv_scanLineColor) {
            this.mScanLineColor = typedArray.getColor(attr, this.mScanLineColor);
        } else if (attr == R.styleable.QRCodeView_qrcv_scanLineMargin) {
            this.mScanLineMargin = typedArray.getDimensionPixelSize(attr, this.mScanLineMargin);
        } else if (attr == R.styleable.QRCodeView_qrcv_isShowDefaultScanLineDrawable) {
            this.mIsShowDefaultScanLineDrawable = typedArray.getBoolean(attr, this.mIsShowDefaultScanLineDrawable);
        } else if (attr == R.styleable.QRCodeView_qrcv_customScanLineDrawable) {
            this.mCustomScanLineDrawable = typedArray.getDrawable(attr);
        } else if (attr == R.styleable.QRCodeView_qrcv_borderSize) {
            this.mBorderSize = typedArray.getDimensionPixelSize(attr, this.mBorderSize);
        } else if (attr == R.styleable.QRCodeView_qrcv_borderColor) {
            this.mBorderColor = typedArray.getColor(attr, this.mBorderColor);
        } else if (attr == R.styleable.QRCodeView_qrcv_animTime) {
            this.mAnimTime = typedArray.getInteger(attr, this.mAnimTime);
        } else if (attr == R.styleable.QRCodeView_qrcv_isCenterVertical) {
            this.mIsCenterVertical = typedArray.getBoolean(attr, this.mIsCenterVertical);
        } else if (attr == R.styleable.QRCodeView_qrcv_toolbarHeight) {
            this.mToolbarHeight = typedArray.getDimensionPixelSize(attr, this.mToolbarHeight);
        } else if (attr == R.styleable.QRCodeView_qrcv_barcodeRectHeight) {
            this.mBarcodeRectHeight = typedArray.getDimensionPixelSize(attr, this.mBarcodeRectHeight);
        } else if (attr == R.styleable.QRCodeView_qrcv_isBarcode) {
            this.mIsBarcode = typedArray.getBoolean(attr, this.mIsBarcode);
        } else if (attr == R.styleable.QRCodeView_qrcv_barCodeTipText) {
            this.mBarCodeTipText = typedArray.getString(attr);
        } else if (attr == R.styleable.QRCodeView_qrcv_qrCodeTipText) {
            this.mQRCodeTipText = typedArray.getString(attr);
        } else if (attr == R.styleable.QRCodeView_qrcv_tipTextSize) {
            this.mTipTextSize = typedArray.getDimensionPixelSize(attr, this.mTipTextSize);
        } else if (attr == R.styleable.QRCodeView_qrcv_tipTextColor) {
            this.mTipTextColor = typedArray.getColor(attr, this.mTipTextColor);
        } else if (attr == R.styleable.QRCodeView_qrcv_isTipTextBelowRect) {
            this.mIsTipTextBelowRect = typedArray.getBoolean(attr, this.mIsTipTextBelowRect);
        } else if (attr == R.styleable.QRCodeView_qrcv_tipTextMargin) {
            this.mTipTextMargin = typedArray.getDimensionPixelSize(attr, this.mTipTextMargin);
        } else if (attr == R.styleable.QRCodeView_qrcv_isShowTipTextAsSingleLine) {
            this.mIsShowTipTextAsSingleLine = typedArray.getBoolean(attr, this.mIsShowTipTextAsSingleLine);
        } else if (attr == R.styleable.QRCodeView_qrcv_isShowTipBackground) {
            this.mIsShowTipBackground = typedArray.getBoolean(attr, this.mIsShowTipBackground);
        } else if (attr == R.styleable.QRCodeView_qrcv_tipBackgroundColor) {
            this.mTipBackgroundColor = typedArray.getColor(attr, this.mTipBackgroundColor);
        } else if (attr == R.styleable.QRCodeView_qrcv_isScanLineReverse) {
            this.mIsScanLineReverse = typedArray.getBoolean(attr, this.mIsScanLineReverse);
        } else if (attr == R.styleable.QRCodeView_qrcv_isShowDefaultGridScanLineDrawable) {
            this.mIsShowDefaultGridScanLineDrawable = typedArray.getBoolean(attr, this.mIsShowDefaultGridScanLineDrawable);
        } else if (attr == R.styleable.QRCodeView_qrcv_customGridScanLineDrawable) {
            this.mCustomGridScanLineDrawable = typedArray.getDrawable(attr);
        } else if (attr == R.styleable.QRCodeView_qrcv_isOnlyDecodeScanBoxArea) {
            this.mIsOnlyDecodeScanBoxArea = typedArray.getBoolean(attr, this.mIsOnlyDecodeScanBoxArea);
        }
    }

    private void afterInitCustomAttrs() {
        Drawable drawable = this.mCustomGridScanLineDrawable;
        if (drawable != null) {
            this.mOriginQRCodeGridScanLineBitmap = ((BitmapDrawable) drawable).getBitmap();
        }
        if (this.mOriginQRCodeGridScanLineBitmap == null) {
            Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.mipmap.qrcode_default_grid_scan_line);
            this.mOriginQRCodeGridScanLineBitmap = decodeResource;
            this.mOriginQRCodeGridScanLineBitmap = BGAQRCodeUtil.makeTintBitmap(decodeResource, this.mScanLineColor);
        }
        Bitmap adjustPhotoRotation = BGAQRCodeUtil.adjustPhotoRotation(this.mOriginQRCodeGridScanLineBitmap, 90);
        this.mOriginBarCodeGridScanLineBitmap = adjustPhotoRotation;
        Bitmap adjustPhotoRotation2 = BGAQRCodeUtil.adjustPhotoRotation(adjustPhotoRotation, 90);
        this.mOriginBarCodeGridScanLineBitmap = adjustPhotoRotation2;
        this.mOriginBarCodeGridScanLineBitmap = BGAQRCodeUtil.adjustPhotoRotation(adjustPhotoRotation2, 90);
        Drawable drawable2 = this.mCustomScanLineDrawable;
        if (drawable2 != null) {
            this.mOriginQRCodeScanLineBitmap = ((BitmapDrawable) drawable2).getBitmap();
        }
        if (this.mOriginQRCodeScanLineBitmap == null) {
            Bitmap decodeResource2 = BitmapFactory.decodeResource(getResources(), R.mipmap.qrcode_default_scan_line);
            this.mOriginQRCodeScanLineBitmap = decodeResource2;
            this.mOriginQRCodeScanLineBitmap = BGAQRCodeUtil.makeTintBitmap(decodeResource2, this.mScanLineColor);
        }
        this.mOriginBarCodeScanLineBitmap = BGAQRCodeUtil.adjustPhotoRotation(this.mOriginQRCodeScanLineBitmap, 90);
        this.mTopOffset += this.mToolbarHeight;
        this.mHalfCornerSize = (this.mCornerSize * 1.0f) / 2.0f;
        this.mTipPaint.setTextSize(this.mTipTextSize);
        this.mTipPaint.setColor(this.mTipTextColor);
        setIsBarcode(this.mIsBarcode);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (this.mFramingRect == null) {
            return;
        }
        drawMask(canvas);
        drawBorderLine(canvas);
        drawCornerLine(canvas);
        drawScanLine(canvas);
        drawTipText(canvas);
        moveScanLine();
    }

    private void drawMask(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        if (this.mMaskColor != 0) {
            this.mPaint.setStyle(Paint.Style.FILL);
            this.mPaint.setColor(this.mMaskColor);
            canvas.drawRect(0.0f, 0.0f, width, this.mFramingRect.top, this.mPaint);
            canvas.drawRect(0.0f, this.mFramingRect.top, this.mFramingRect.left, this.mFramingRect.bottom + 1, this.mPaint);
            canvas.drawRect(this.mFramingRect.right + 1, this.mFramingRect.top, width, this.mFramingRect.bottom + 1, this.mPaint);
            canvas.drawRect(0.0f, this.mFramingRect.bottom + 1, width, height, this.mPaint);
        }
    }

    private void drawBorderLine(Canvas canvas) {
        if (this.mBorderSize > 0) {
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setColor(this.mBorderColor);
            this.mPaint.setStrokeWidth(this.mBorderSize);
            canvas.drawRect(this.mFramingRect, this.mPaint);
        }
    }

    private void drawCornerLine(Canvas canvas) {
        if (this.mHalfCornerSize > 0.0f) {
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setColor(this.mCornerColor);
            this.mPaint.setStrokeWidth(this.mCornerSize);
            canvas.drawLine(this.mFramingRect.left - this.mHalfCornerSize, this.mFramingRect.top, (this.mFramingRect.left - this.mHalfCornerSize) + this.mCornerLength, this.mFramingRect.top, this.mPaint);
            canvas.drawLine(this.mFramingRect.left, this.mFramingRect.top - this.mHalfCornerSize, this.mFramingRect.left, (this.mFramingRect.top - this.mHalfCornerSize) + this.mCornerLength, this.mPaint);
            canvas.drawLine(this.mFramingRect.right + this.mHalfCornerSize, this.mFramingRect.top, (this.mFramingRect.right + this.mHalfCornerSize) - this.mCornerLength, this.mFramingRect.top, this.mPaint);
            canvas.drawLine(this.mFramingRect.right, this.mFramingRect.top - this.mHalfCornerSize, this.mFramingRect.right, (this.mFramingRect.top - this.mHalfCornerSize) + this.mCornerLength, this.mPaint);
            canvas.drawLine(this.mFramingRect.left - this.mHalfCornerSize, this.mFramingRect.bottom, (this.mFramingRect.left - this.mHalfCornerSize) + this.mCornerLength, this.mFramingRect.bottom, this.mPaint);
            canvas.drawLine(this.mFramingRect.left, this.mFramingRect.bottom + this.mHalfCornerSize, this.mFramingRect.left, (this.mFramingRect.bottom + this.mHalfCornerSize) - this.mCornerLength, this.mPaint);
            canvas.drawLine(this.mFramingRect.right + this.mHalfCornerSize, this.mFramingRect.bottom, (this.mFramingRect.right + this.mHalfCornerSize) - this.mCornerLength, this.mFramingRect.bottom, this.mPaint);
            canvas.drawLine(this.mFramingRect.right, this.mFramingRect.bottom + this.mHalfCornerSize, this.mFramingRect.right, (this.mFramingRect.bottom + this.mHalfCornerSize) - this.mCornerLength, this.mPaint);
        }
    }

    private void drawScanLine(Canvas canvas) {
        if (this.mIsBarcode) {
            if (this.mGridScanLineBitmap != null) {
                RectF dstGridRectF = new RectF(this.mFramingRect.left + this.mHalfCornerSize + 0.5f, this.mFramingRect.top + this.mHalfCornerSize + this.mScanLineMargin, this.mGridScanLineRight, (this.mFramingRect.bottom - this.mHalfCornerSize) - this.mScanLineMargin);
                Rect srcGridRect = new Rect((int) (this.mGridScanLineBitmap.getWidth() - dstGridRectF.width()), 0, this.mGridScanLineBitmap.getWidth(), this.mGridScanLineBitmap.getHeight());
                if (srcGridRect.left < 0) {
                    srcGridRect.left = 0;
                    dstGridRectF.left = dstGridRectF.right - srcGridRect.width();
                }
                canvas.drawBitmap(this.mGridScanLineBitmap, srcGridRect, dstGridRectF, this.mPaint);
            } else if (this.mScanLineBitmap != null) {
                RectF lineRect = new RectF(this.mScanLineLeft, this.mFramingRect.top + this.mHalfCornerSize + this.mScanLineMargin, this.mScanLineLeft + this.mScanLineBitmap.getWidth(), (this.mFramingRect.bottom - this.mHalfCornerSize) - this.mScanLineMargin);
                canvas.drawBitmap(this.mScanLineBitmap, (Rect) null, lineRect, this.mPaint);
            } else {
                this.mPaint.setStyle(Paint.Style.FILL);
                this.mPaint.setColor(this.mScanLineColor);
                canvas.drawRect(this.mScanLineLeft, this.mFramingRect.top + this.mHalfCornerSize + this.mScanLineMargin, this.mScanLineLeft + this.mScanLineSize, (this.mFramingRect.bottom - this.mHalfCornerSize) - this.mScanLineMargin, this.mPaint);
            }
        } else if (this.mGridScanLineBitmap != null) {
            RectF dstGridRectF2 = new RectF(this.mFramingRect.left + this.mHalfCornerSize + this.mScanLineMargin, this.mFramingRect.top + this.mHalfCornerSize + 0.5f, (this.mFramingRect.right - this.mHalfCornerSize) - this.mScanLineMargin, this.mGridScanLineBottom);
            Rect srcRect = new Rect(0, (int) (this.mGridScanLineBitmap.getHeight() - dstGridRectF2.height()), this.mGridScanLineBitmap.getWidth(), this.mGridScanLineBitmap.getHeight());
            if (srcRect.top < 0) {
                srcRect.top = 0;
                dstGridRectF2.top = dstGridRectF2.bottom - srcRect.height();
            }
            canvas.drawBitmap(this.mGridScanLineBitmap, srcRect, dstGridRectF2, this.mPaint);
        } else if (this.mScanLineBitmap != null) {
            RectF lineRect2 = new RectF(this.mFramingRect.left + this.mHalfCornerSize + this.mScanLineMargin, this.mScanLineTop, (this.mFramingRect.right - this.mHalfCornerSize) - this.mScanLineMargin, this.mScanLineTop + this.mScanLineBitmap.getHeight());
            canvas.drawBitmap(this.mScanLineBitmap, (Rect) null, lineRect2, this.mPaint);
        } else {
            this.mPaint.setStyle(Paint.Style.FILL);
            this.mPaint.setColor(this.mScanLineColor);
            canvas.drawRect(this.mFramingRect.left + this.mHalfCornerSize + this.mScanLineMargin, this.mScanLineTop, (this.mFramingRect.right - this.mHalfCornerSize) - this.mScanLineMargin, this.mScanLineTop + this.mScanLineSize, this.mPaint);
        }
    }

    private void drawTipText(Canvas canvas) {
        if (TextUtils.isEmpty(this.mTipText) || this.mTipTextSl == null) {
            return;
        }
        if (this.mIsTipTextBelowRect) {
            if (this.mIsShowTipBackground) {
                this.mPaint.setColor(this.mTipBackgroundColor);
                this.mPaint.setStyle(Paint.Style.FILL);
                if (this.mIsShowTipTextAsSingleLine) {
                    Rect tipRect = new Rect();
                    TextPaint textPaint = this.mTipPaint;
                    String str = this.mTipText;
                    textPaint.getTextBounds(str, 0, str.length(), tipRect);
                    float left = ((canvas.getWidth() - tipRect.width()) / 2) - this.mTipBackgroundRadius;
                    RectF rectF = new RectF(left, (this.mFramingRect.bottom + this.mTipTextMargin) - this.mTipBackgroundRadius, tipRect.width() + left + (this.mTipBackgroundRadius * 2), this.mFramingRect.bottom + this.mTipTextMargin + this.mTipTextSl.getHeight() + this.mTipBackgroundRadius);
                    int i = this.mTipBackgroundRadius;
                    canvas.drawRoundRect(rectF, i, i, this.mPaint);
                } else {
                    RectF rectF2 = new RectF(this.mFramingRect.left, (this.mFramingRect.bottom + this.mTipTextMargin) - this.mTipBackgroundRadius, this.mFramingRect.right, this.mFramingRect.bottom + this.mTipTextMargin + this.mTipTextSl.getHeight() + this.mTipBackgroundRadius);
                    int i2 = this.mTipBackgroundRadius;
                    canvas.drawRoundRect(rectF2, i2, i2, this.mPaint);
                }
            }
            canvas.save();
            if (this.mIsShowTipTextAsSingleLine) {
                canvas.translate(0.0f, this.mFramingRect.bottom + this.mTipTextMargin);
            } else {
                canvas.translate(this.mFramingRect.left + this.mTipBackgroundRadius, this.mFramingRect.bottom + this.mTipTextMargin);
            }
            this.mTipTextSl.draw(canvas);
            canvas.restore();
            return;
        }
        if (this.mIsShowTipBackground) {
            this.mPaint.setColor(this.mTipBackgroundColor);
            this.mPaint.setStyle(Paint.Style.FILL);
            if (this.mIsShowTipTextAsSingleLine) {
                Rect tipRect2 = new Rect();
                TextPaint textPaint2 = this.mTipPaint;
                String str2 = this.mTipText;
                textPaint2.getTextBounds(str2, 0, str2.length(), tipRect2);
                float left2 = ((canvas.getWidth() - tipRect2.width()) / 2) - this.mTipBackgroundRadius;
                RectF rectF3 = new RectF(left2, ((this.mFramingRect.top - this.mTipTextMargin) - this.mTipTextSl.getHeight()) - this.mTipBackgroundRadius, tipRect2.width() + left2 + (this.mTipBackgroundRadius * 2), (this.mFramingRect.top - this.mTipTextMargin) + this.mTipBackgroundRadius);
                int i3 = this.mTipBackgroundRadius;
                canvas.drawRoundRect(rectF3, i3, i3, this.mPaint);
            } else {
                RectF rectF4 = new RectF(this.mFramingRect.left, ((this.mFramingRect.top - this.mTipTextMargin) - this.mTipTextSl.getHeight()) - this.mTipBackgroundRadius, this.mFramingRect.right, (this.mFramingRect.top - this.mTipTextMargin) + this.mTipBackgroundRadius);
                int i4 = this.mTipBackgroundRadius;
                canvas.drawRoundRect(rectF4, i4, i4, this.mPaint);
            }
        }
        canvas.save();
        if (this.mIsShowTipTextAsSingleLine) {
            canvas.translate(0.0f, (this.mFramingRect.top - this.mTipTextMargin) - this.mTipTextSl.getHeight());
        } else {
            canvas.translate(this.mFramingRect.left + this.mTipBackgroundRadius, (this.mFramingRect.top - this.mTipTextMargin) - this.mTipTextSl.getHeight());
        }
        this.mTipTextSl.draw(canvas);
        canvas.restore();
    }

    private void moveScanLine() {
        if (this.mIsBarcode) {
            if (this.mGridScanLineBitmap == null) {
                this.mScanLineLeft += this.mMoveStepDistance;
                int scanLineSize = this.mScanLineSize;
                Bitmap bitmap = this.mScanLineBitmap;
                if (bitmap != null) {
                    scanLineSize = bitmap.getWidth();
                }
                if (this.mIsScanLineReverse) {
                    if (this.mScanLineLeft + scanLineSize > this.mFramingRect.right - this.mHalfCornerSize || this.mScanLineLeft < this.mFramingRect.left + this.mHalfCornerSize) {
                        this.mMoveStepDistance = -this.mMoveStepDistance;
                    }
                } else if (this.mScanLineLeft + scanLineSize > this.mFramingRect.right - this.mHalfCornerSize) {
                    this.mScanLineLeft = this.mFramingRect.left + this.mHalfCornerSize + 0.5f;
                }
            } else {
                float f = this.mGridScanLineRight + this.mMoveStepDistance;
                this.mGridScanLineRight = f;
                if (f > this.mFramingRect.right - this.mHalfCornerSize) {
                    this.mGridScanLineRight = this.mFramingRect.left + this.mHalfCornerSize + 0.5f;
                }
            }
        } else if (this.mGridScanLineBitmap == null) {
            this.mScanLineTop += this.mMoveStepDistance;
            int scanLineSize2 = this.mScanLineSize;
            Bitmap bitmap2 = this.mScanLineBitmap;
            if (bitmap2 != null) {
                scanLineSize2 = bitmap2.getHeight();
            }
            if (this.mIsScanLineReverse) {
                if (this.mScanLineTop + scanLineSize2 > this.mFramingRect.bottom - this.mHalfCornerSize || this.mScanLineTop < this.mFramingRect.top + this.mHalfCornerSize) {
                    this.mMoveStepDistance = -this.mMoveStepDistance;
                }
            } else if (this.mScanLineTop + scanLineSize2 > this.mFramingRect.bottom - this.mHalfCornerSize) {
                this.mScanLineTop = this.mFramingRect.top + this.mHalfCornerSize + 0.5f;
            }
        } else {
            float f2 = this.mGridScanLineBottom + this.mMoveStepDistance;
            this.mGridScanLineBottom = f2;
            if (f2 > this.mFramingRect.bottom - this.mHalfCornerSize) {
                this.mGridScanLineBottom = this.mFramingRect.top + this.mHalfCornerSize + 0.5f;
            }
        }
        postInvalidateDelayed(this.mAnimDelayTime, this.mFramingRect.left, this.mFramingRect.top, this.mFramingRect.right, this.mFramingRect.bottom);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calFramingRect();
    }

    private void calFramingRect() {
        int leftOffset = (getWidth() - this.mRectWidth) / 2;
        int i = this.mTopOffset;
        Rect rect = new Rect(leftOffset, i, this.mRectWidth + leftOffset, this.mRectHeight + i);
        this.mFramingRect = rect;
        if (this.mIsBarcode) {
            float f = rect.left + this.mHalfCornerSize + 0.5f;
            this.mScanLineLeft = f;
            this.mGridScanLineRight = f;
            return;
        }
        float f2 = rect.top + this.mHalfCornerSize + 0.5f;
        this.mScanLineTop = f2;
        this.mGridScanLineBottom = f2;
    }

    public Rect getScanBoxAreaRect(int previewHeight) {
        if (this.mIsOnlyDecodeScanBoxArea) {
            Rect rect = new Rect(this.mFramingRect);
            float ratio = (previewHeight * 1.0f) / getMeasuredHeight();
            rect.left = (int) (rect.left * ratio);
            rect.right = (int) (rect.right * ratio);
            rect.top = (int) (rect.top * ratio);
            rect.bottom = (int) (rect.bottom * ratio);
            return rect;
        }
        return null;
    }

    public void setIsBarcode(boolean isBarcode) {
        this.mIsBarcode = isBarcode;
        if (this.mCustomGridScanLineDrawable != null || this.mIsShowDefaultGridScanLineDrawable) {
            if (isBarcode) {
                this.mGridScanLineBitmap = this.mOriginBarCodeGridScanLineBitmap;
            } else {
                this.mGridScanLineBitmap = this.mOriginQRCodeGridScanLineBitmap;
            }
        } else if (this.mCustomScanLineDrawable != null || this.mIsShowDefaultScanLineDrawable) {
            if (isBarcode) {
                this.mScanLineBitmap = this.mOriginBarCodeScanLineBitmap;
            } else {
                this.mScanLineBitmap = this.mOriginQRCodeScanLineBitmap;
            }
        }
        if (isBarcode) {
            this.mTipText = this.mBarCodeTipText;
            this.mRectHeight = this.mBarcodeRectHeight;
            this.mAnimDelayTime = (int) (((this.mAnimTime * 1.0f) * this.mMoveStepDistance) / this.mRectWidth);
        } else {
            this.mTipText = this.mQRCodeTipText;
            int i = this.mRectWidth;
            this.mRectHeight = i;
            this.mAnimDelayTime = (int) (((this.mAnimTime * 1.0f) * this.mMoveStepDistance) / i);
        }
        if (!TextUtils.isEmpty(this.mTipText)) {
            if (this.mIsShowTipTextAsSingleLine) {
                this.mTipTextSl = new StaticLayout(this.mTipText, this.mTipPaint, BGAQRCodeUtil.getScreenResolution(getContext()).x, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, true);
            } else {
                this.mTipTextSl = new StaticLayout(this.mTipText, this.mTipPaint, this.mRectWidth - (this.mTipBackgroundRadius * 2), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, true);
            }
        }
        if (this.mIsCenterVertical) {
            int screenHeight = BGAQRCodeUtil.getScreenResolution(getContext()).y;
            int i2 = this.mToolbarHeight;
            if (i2 == 0) {
                this.mTopOffset = (screenHeight - this.mRectHeight) / 2;
            } else {
                this.mTopOffset = ((screenHeight - this.mRectHeight) / 2) + (i2 / 2);
            }
        }
        calFramingRect();
        postInvalidate();
    }

    public boolean getIsBarcode() {
        return this.mIsBarcode;
    }

    public int getMaskColor() {
        return this.mMaskColor;
    }

    public void setMaskColor(int maskColor) {
        this.mMaskColor = maskColor;
    }

    public int getCornerColor() {
        return this.mCornerColor;
    }

    public void setCornerColor(int cornerColor) {
        this.mCornerColor = cornerColor;
    }

    public int getCornerLength() {
        return this.mCornerLength;
    }

    public void setCornerLength(int cornerLength) {
        this.mCornerLength = cornerLength;
    }

    public int getCornerSize() {
        return this.mCornerSize;
    }

    public void setCornerSize(int cornerSize) {
        this.mCornerSize = cornerSize;
    }

    public int getRectWidth() {
        return this.mRectWidth;
    }

    public void setRectWidth(int rectWidth) {
        this.mRectWidth = rectWidth;
    }

    public int getRectHeight() {
        return this.mRectHeight;
    }

    public void setRectHeight(int rectHeight) {
        this.mRectHeight = rectHeight;
    }

    public int getBarcodeRectHeight() {
        return this.mBarcodeRectHeight;
    }

    public void setBarcodeRectHeight(int barcodeRectHeight) {
        this.mBarcodeRectHeight = barcodeRectHeight;
    }

    public int getTopOffset() {
        return this.mTopOffset;
    }

    public void setTopOffset(int topOffset) {
        this.mTopOffset = topOffset;
    }

    public int getScanLineSize() {
        return this.mScanLineSize;
    }

    public void setScanLineSize(int scanLineSize) {
        this.mScanLineSize = scanLineSize;
    }

    public int getScanLineColor() {
        return this.mScanLineColor;
    }

    public void setScanLineColor(int scanLineColor) {
        this.mScanLineColor = scanLineColor;
    }

    public int getScanLineMargin() {
        return this.mScanLineMargin;
    }

    public void setScanLineMargin(int scanLineMargin) {
        this.mScanLineMargin = scanLineMargin;
    }

    public boolean isShowDefaultScanLineDrawable() {
        return this.mIsShowDefaultScanLineDrawable;
    }

    public void setShowDefaultScanLineDrawable(boolean showDefaultScanLineDrawable) {
        this.mIsShowDefaultScanLineDrawable = showDefaultScanLineDrawable;
    }

    public Drawable getCustomScanLineDrawable() {
        return this.mCustomScanLineDrawable;
    }

    public void setCustomScanLineDrawable(Drawable customScanLineDrawable) {
        this.mCustomScanLineDrawable = customScanLineDrawable;
    }

    public Bitmap getScanLineBitmap() {
        return this.mScanLineBitmap;
    }

    public void setScanLineBitmap(Bitmap scanLineBitmap) {
        this.mScanLineBitmap = scanLineBitmap;
    }

    public int getBorderSize() {
        return this.mBorderSize;
    }

    public void setBorderSize(int borderSize) {
        this.mBorderSize = borderSize;
    }

    public int getBorderColor() {
        return this.mBorderColor;
    }

    public void setBorderColor(int borderColor) {
        this.mBorderColor = borderColor;
    }

    public int getAnimTime() {
        return this.mAnimTime;
    }

    public void setAnimTime(int animTime) {
        this.mAnimTime = animTime;
    }

    public boolean isCenterVertical() {
        return this.mIsCenterVertical;
    }

    public void setCenterVertical(boolean centerVertical) {
        this.mIsCenterVertical = centerVertical;
    }

    public int getToolbarHeight() {
        return this.mToolbarHeight;
    }

    public void setToolbarHeight(int toolbarHeight) {
        this.mToolbarHeight = toolbarHeight;
    }

    public String getQRCodeTipText() {
        return this.mQRCodeTipText;
    }

    public void setQRCodeTipText(String qrCodeTipText) {
        this.mQRCodeTipText = qrCodeTipText;
    }

    public String getBarCodeTipText() {
        return this.mBarCodeTipText;
    }

    public void setBarCodeTipText(String barCodeTipText) {
        this.mBarCodeTipText = barCodeTipText;
    }

    public String getTipText() {
        return this.mTipText;
    }

    public void setTipText(String tipText) {
        this.mTipText = tipText;
    }

    public int getTipTextColor() {
        return this.mTipTextColor;
    }

    public void setTipTextColor(int tipTextColor) {
        this.mTipTextColor = tipTextColor;
    }

    public int getTipTextSize() {
        return this.mTipTextSize;
    }

    public void setTipTextSize(int tipTextSize) {
        this.mTipTextSize = tipTextSize;
    }

    public boolean isTipTextBelowRect() {
        return this.mIsTipTextBelowRect;
    }

    public void setTipTextBelowRect(boolean tipTextBelowRect) {
        this.mIsTipTextBelowRect = tipTextBelowRect;
    }

    public int getTipTextMargin() {
        return this.mTipTextMargin;
    }

    public void setTipTextMargin(int tipTextMargin) {
        this.mTipTextMargin = tipTextMargin;
    }

    public boolean isShowTipTextAsSingleLine() {
        return this.mIsShowTipTextAsSingleLine;
    }

    public void setShowTipTextAsSingleLine(boolean showTipTextAsSingleLine) {
        this.mIsShowTipTextAsSingleLine = showTipTextAsSingleLine;
    }

    public boolean isShowTipBackground() {
        return this.mIsShowTipBackground;
    }

    public void setShowTipBackground(boolean showTipBackground) {
        this.mIsShowTipBackground = showTipBackground;
    }

    public int getTipBackgroundColor() {
        return this.mTipBackgroundColor;
    }

    public void setTipBackgroundColor(int tipBackgroundColor) {
        this.mTipBackgroundColor = tipBackgroundColor;
    }

    public boolean isScanLineReverse() {
        return this.mIsScanLineReverse;
    }

    public void setScanLineReverse(boolean scanLineReverse) {
        this.mIsScanLineReverse = scanLineReverse;
    }

    public boolean isShowDefaultGridScanLineDrawable() {
        return this.mIsShowDefaultGridScanLineDrawable;
    }

    public void setShowDefaultGridScanLineDrawable(boolean showDefaultGridScanLineDrawable) {
        this.mIsShowDefaultGridScanLineDrawable = showDefaultGridScanLineDrawable;
    }

    public float getHalfCornerSize() {
        return this.mHalfCornerSize;
    }

    public void setHalfCornerSize(float halfCornerSize) {
        this.mHalfCornerSize = halfCornerSize;
    }

    public StaticLayout getTipTextSl() {
        return this.mTipTextSl;
    }

    public void setTipTextSl(StaticLayout tipTextSl) {
        this.mTipTextSl = tipTextSl;
    }

    public int getTipBackgroundRadius() {
        return this.mTipBackgroundRadius;
    }

    public void setTipBackgroundRadius(int tipBackgroundRadius) {
        this.mTipBackgroundRadius = tipBackgroundRadius;
    }

    public boolean isOnlyDecodeScanBoxArea() {
        return this.mIsOnlyDecodeScanBoxArea;
    }

    public void setOnlyDecodeScanBoxArea(boolean onlyDecodeScanBoxArea) {
        this.mIsOnlyDecodeScanBoxArea = onlyDecodeScanBoxArea;
    }
}
