package com.zpj.collection;

/**
 * @author PerKins Zhu
 * @date:2016年9月2日 下午9:03:35
 * @version :1.1
 * 
 */

public class PageCollection<T extends Comparable<T>> {

	private Node<T> root;

	private static class Node<T> {
		Node<T> left;
		Node<T> right;
		T data;
		int height;

		public Node(Node<T> left, Node<T> right, T data) {
			this.left = left;
			this.right = right;
			this.data = data;
			this.height = 0;
		}
	}

	/**
	 * 如果树中已经存在该节点则不进行插入，如果没有该节点则进行插入 判断是否存在该节点的方法是compareTo(T node) == 0。
	 * 
	 * @param data
	 * @return
	 */
	public Node<T> insert(T data) {
		return root = insert(data, root);
	}

	private Node<T> insert(T data, Node<T> node) {
		if (node == null)
			return new Node<T>(null, null, data);
		int compareResult = data.compareTo(node.data);
		if (compareResult > 0) {
			node.right = insert(data, node.right);
			if (getHeight(node.right) - getHeight(node.left) == 2) {
				int compareResult02 = data.compareTo(node.right.data);
				if (compareResult02 > 0)
					node = rotateSingleLeft(node);
				else
					node = rotateDoubleLeft(node);
			}
		} else if (compareResult < 0) {
			node.left = insert(data, node.left);
			if (getHeight(node.left) - getHeight(node.right) == 2) {
				int intcompareResult02 = data.compareTo(node.left.data);
				if (intcompareResult02 < 0)
					node = rotateSingleRight(node);
				else
					node = rotateDoubleRight(node);
			}
		}
		node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
		return node;
	}

	private Node<T> rotateSingleLeft(Node<T> node) {
		Node<T> rightNode = node.right;
		node.right = rightNode.left;
		rightNode.left = node;
		node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
		rightNode.height = Math.max(node.height, getHeight(rightNode.right)) + 1;
		return rightNode;
	}

	private Node<T> rotateSingleRight(Node<T> node) {
		Node<T> leftNode = node.left;
		node.left = leftNode.right;
		leftNode.right = node;
		node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
		leftNode.height = Math.max(getHeight(leftNode.left), node.height) + 1;
		return leftNode;
	}

	private Node<T> rotateDoubleLeft(Node<T> node) {
		node.right = rotateSingleRight(node.right);
		node = rotateSingleLeft(node);
		return node;
	}

	private Node<T> rotateDoubleRight(Node<T> node) {
		node.left = rotateSingleLeft(node.left);
		node = rotateSingleRight(node);
		return node;
	}

	private int getHeight(Node<T> node) {
		return node == null ? -1 : node.height;
	}

	public Node<T> remove(T data) {
		return root = remove(data, root);
	}

	private Node<T> remove(T data, Node<T> node) {
		if (node == null) {
			return null;
		}
		int compareResult = data.compareTo(node.data);
		if (compareResult == 0) {
			if (node.left != null && node.right != null) {
				int balance = getHeight(node.left) - getHeight(node.right);
				Node<T> temp = node;
				if (balance == -1) {
					exChangeRightData(node, node.right);
				} else {
					exChangeLeftData(node, node.left);
				}
				temp.height = Math.max(getHeight(temp.left), getHeight(temp.right)) + 1;
				return temp;
			} else {
				return node.left != null ? node.left : node.right;
			}
		} else if (compareResult > 0) {
			node.right = remove(data, node.right);
			node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
			if (getHeight(node.left) - getHeight(node.right) == 2) {
				Node<T> leftSon = node.left;
				if (leftSon.left.height > leftSon.right.height) {
					node = rotateSingleRight(node);
				} else {
					node = rotateDoubleRight(node);
				}
			}
			return node;
		} else if (compareResult < 0) {
			node.left = remove(data, node.left);
			node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
			if (getHeight(node.left) - getHeight(node.right) == 2) {
				Node<T> rightSon = node.right;
				if (rightSon.right.height > rightSon.left.height) {
					node = rotateSingleLeft(node);
				} else {
					node = rotateDoubleLeft(node);
				}
			}
			return node;
		}
		return null;
	}

	private Node<T> exChangeLeftData(Node<T> node, Node<T> right) {
		if (right.right != null) {
			right.right = exChangeLeftData(node, right.right);
		} else {
			node.data = right.data;
			return right.left;
		}
		right.height = Math.max(getHeight(right.left), getHeight(right.right)) + 1;
		int isbanlance = getHeight(right.left) - getHeight(right.right);
		if (isbanlance == 2) {
			Node<T> leftSon = node.left;
			if (leftSon.left.height > leftSon.right.height) {
				return node = rotateSingleRight(node);
			} else {
				return node = rotateDoubleRight(node);
			}
		}
		return right;
	}

	private Node<T> exChangeRightData(Node<T> node, Node<T> left) {
		if (left.left != null) {
			left.left = exChangeRightData(node, left.left);
		} else {
			node.data = left.data;
			return left.right;
		}
		left.height = Math.max(getHeight(left.left), getHeight(left.right)) + 1;
		int isbanlance = getHeight(left.left) - getHeight(left.right);
		if (isbanlance == -2) {
			Node<T> rightSon = node.right;
			if (rightSon.right.height > rightSon.left.height) {
				return node = rotateSingleLeft(node);
			} else {
				return node = rotateDoubleLeft(node);
			}
		}
		return left;
	}

	public void inorderTraverse() {
		inorderTraverseData(root);
	}

	private void inorderTraverseData(Node<T> node) {
		if (node.left != null) {
			inorderTraverseData(node.left);
		}
		System.out.print(node.data + "、");
		if (node.right != null) {
			inorderTraverseData(node.right);
		}
	}

	public boolean isEmpty() {
		return root == null;
	}

	// 取出最大权重的节点返回 ，同时删除该节点
	public T getMaxNode() {
		if (root != null)
			root = getMaxNode(root);
		else
			return null;
		return maxNode.data;
	}

	private Node<T> maxNode;

	private Node<T> getMaxNode(Node<T> node) {
		if (node.right == null) {
			maxNode = node;
			return node.left;
		}
		node.right = getMaxNode(node.right);
		node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
		if (getHeight(node.left) - getHeight(node.right) == 2) {
			Node<T> leftSon = node.left;
			if (isDoubleRotate(leftSon.left, leftSon.right)) {
				node = rotateDoubleRight(node);
			} else {
				node = rotateSingleRight(node);
			}
		}
		return node;
	}

	// 根据节点的树高判断是否需要进行两次旋转 node01是外部节点，node02是内部节点(前提是该节点的祖父节点需要进行旋转)
	private boolean isDoubleRotate(Node<T> node01, Node<T> node02) {
		// 内部节点不存在，不需要进行两次旋转
		if (node02 == null)
			return false;
		// 外部节点等于null，则内部节点树高必为2，进行两侧旋转
		// 外部节点树高小于内部节点树高则必定要进行两次旋转
		if (node01 == null || node01.height < node02.height)
			return true;
		return false;
	}

	public T get(T data) {
		if (root == null)
			return null;
		return get(data, root);
	}

	private T get(T data, Node<T> node) {
		if (node == null)
			return null;
		int temp = data.compareTo(node.data);
		if (temp == 0) {
			return node.data;
		} else if (temp > 0) {
			return get(data, node.right);
		} else if (temp < 0) {
			return get(data, node.left);
		}
		return null;
	}

}
