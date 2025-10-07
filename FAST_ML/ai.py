from fastapi import FastAPI, Request, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from transformers import pipeline, AutoTokenizer, AutoModelForSequenceClassification
import torch
app = FastAPI()


app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

MODEL_PATH = r"D:\Real-Time-Crisis-Sentiment-Analysis\Colab_Sentiment-Model\my_model"
tokenizer = AutoTokenizer.from_pretrained(MODEL_PATH)
model = AutoModelForSequenceClassification.from_pretrained(MODEL_PATH)

classifier = pipeline("zero-shot-classification", model=model, tokenizer=tokenizer)
candidate_labels = ["urgent", "informational", "misinformation"]

@app.post("/predict")
async def predict(request: Request):
    try:
        data = await request.json()
        text = data.get("text")

        if not text:
            raise HTTPException(status_code=400, detail="No text provided")

        result = classifier(text, candidate_labels)
        top_label = result["labels"][0]
        top_score = result["scores"][0]

        if top_label == "urgent" and top_score >= 0.74:
            return {
                "prediction": "urgent",
                "confidence": float(top_score),
                "all_scores": dict(zip(result["labels"], [round(score, 4) for score in result["scores"]]))
            }
        else:
            return {}

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

