import unittest

def additonner(a,b):
    return a + b


class TestsUnitaireDemo(unittest.TestCase):
    def setUp(self):
        print("setUp")
    def test_bob(self):
        print("TOTO")
    def test_additionner(self):
        self.assertEqual(additonner(5, 10), 15)

unittest.main()