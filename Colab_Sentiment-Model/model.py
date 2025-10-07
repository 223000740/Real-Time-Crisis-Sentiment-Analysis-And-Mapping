from transformers import AutoTokenizer, AutoModelForSequenceClassification

model_name = "facebook/bart-large-mnli"
tokenizer = AutoTokenizer.from_pretrained(model_name)
model = AutoModelForSequenceClassification.from_pretrained(model_name)

tokenizer.save_pretrained("my_model")
model.save_pretrained("my_model")