export interface Category {
  id?: number;
  name: string;
  description?: string;
}

export interface Product {
  id?: number;
  name: string;
  description: string;
  price: number;
  imageUrl: string;
  category?: Category;
}

export interface User {
  id?: number;
  email: string;
  password?: string;
  firstName?: string;
  lastName?: string;
}
