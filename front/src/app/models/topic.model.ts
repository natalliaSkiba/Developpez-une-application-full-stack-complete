export interface Topic {
  id: number;
  name: string;
  description: string;
}

export interface TopicResponse {
  id: number;
  name: string;
  description: string;
  subscribed: boolean;
}

