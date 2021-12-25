package com.CouponSystemSpring.entities;

public enum Category {

		Food("Food"), 
		Electricity("Electricity"), 
		Resturant("Resturant"), 
		Vacation("Vacation");
		
		private String category;
		
		private Category(String category) {
			this.category=category;
		}

		public String getCategory() {
			return category;
		}
		
//		public static Category getFromIndex(int i) {
//			for (Category category : Category.values()) {
//				if (category.ordinal()==i) {
//					return category;
//				}
//			}
//			return null;
//			
//		}
		public static boolean isItCategory(String category) {
			for (Category currCategory : Category.values()) {
				if (currCategory.getCategory().equals(category)) {
					return true;
				}
			}
			return false;
		}
		
		public static void printAllCategory() {
			for (Category category : Category.values()) {
				System.out.print(category+" | ");
			}
		}

//		public static List<Category> getAllCategories() {
//			List<Category> allCategories=new ArrayList<>();
//			for (Category category : Category.values()) {
//				allCategories.add(category);
//			}
//			return allCategories;
//		}
}
